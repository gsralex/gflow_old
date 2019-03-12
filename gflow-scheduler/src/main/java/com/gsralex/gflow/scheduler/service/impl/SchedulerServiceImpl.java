package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.executor.client.ExecutorClient;
import com.gsralex.gflow.executor.client.ExecutorClientFactory;
import com.gsralex.gflow.executor.client.action.JobReq;
import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.context.Parameter;
import com.gsralex.gflow.pub.domain.Action;
import com.gsralex.gflow.pub.domain.Job;
import com.gsralex.gflow.pub.domain.JobGroup;
import com.gsralex.gflow.pub.enums.JobGroupStatusEnum;
import com.gsralex.gflow.pub.enums.JobStatusEnum;
import com.gsralex.gflow.pub.util.DtUtils;
import com.gsralex.gflow.pub.util.SecurityUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.AckReq;
import com.gsralex.gflow.scheduler.dao.ActionDao;
import com.gsralex.gflow.scheduler.dao.JobDao;
import com.gsralex.gflow.scheduler.flow.FlowGuide;
import com.gsralex.gflow.scheduler.flow.FlowMapHandle;
import com.gsralex.gflow.scheduler.flow.FlowNode;
import com.gsralex.gflow.scheduler.parameter.DynamicParamContext;
import com.gsralex.gflow.scheduler.schedule.ActionDesc;
import com.gsralex.gflow.scheduler.schedule.ActionResult;
import com.gsralex.gflow.scheduler.schedule.FlowResult;
import com.gsralex.gflow.scheduler.service.SchedulerService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 实际调度执行者
 *
 * @author gsralex
 * @version 2018/6/2
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private ActionDao actionDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private FlowMapHandle flowMapHandle;
    private SchedulerContext context = SchedulerContext.getInstance();


    @Override
    public FlowResult scheduleGroup(long triggerGroupId,
                                    String parameter,
                                    long timerConfigId,
                                    boolean retry) {
        Parameter param = new Parameter(parameter);
        DynamicParamContext.getContext().parser(param);

        JobGroup jobGroup = new JobGroup();
        jobGroup.setStartTime(System.currentTimeMillis());
        jobGroup.setCreateTime(System.currentTimeMillis());
        jobGroup.setStatus(JobGroupStatusEnum.EXECUTING.getValue());
        jobGroup.setFlowGroupId(triggerGroupId);
        jobGroup.setDate(DtUtils.getBizDate());
        jobGroup.setTimerConfigId(timerConfigId);
        jobGroup.setParameter(param.toString());
        jobDao.saveJobGroup(jobGroup);

        FlowGuide flowGuide = flowMapHandle.initGroup(jobGroup.getId(), triggerGroupId);
        List<FlowNode> rootList = flowGuide.listRoot();
        FlowResult result = doActionList(triggerGroupId, jobGroup.getId(), parameter, rootList, retry);
        result.setJobGroupId(jobGroup.getId());
        return result;
    }

    private FlowResult doActionList(long triggerGroupId,
                                    long jobGroupId,
                                    String groupParameter,
                                    List<FlowNode> nodeList,
                                    boolean retry) {
        FlowResult r = new FlowResult();
        for (FlowNode node : nodeList) {
            ActionDesc desc = new ActionDesc();
            desc.setTriggerGroupId(triggerGroupId);
            desc.setJobGroupId(jobGroupId);
            desc.setActionId(node.getActionId());
            desc.setIndex(node.getIndex());
            desc.setGroupParameter(groupParameter);
            desc.setParameter(node.getParameter());
            ActionResult result = scheduleAction(desc, retry);
            r.getResults().add(result);
        }
        return r;
    }

    @Override
    public void pauseGroup(long jobGroupId) {
        JobGroup jobGroup = jobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(jobGroupId);
            flowGuide.setStatus(JobGroupStatusEnum.PAUSE);
            //更新数据库
            jobGroup.setStatus(JobGroupStatusEnum.PAUSE.getValue());
            jobDao.updateJobGroup(jobGroup);
        }
    }

    @Override
    public void stopGroup(long jobGroupId) {
        JobGroup jobGroup = jobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(jobGroupId);
            flowGuide.setStatus(JobGroupStatusEnum.STOP);
            jobGroup.setStatus(JobGroupStatusEnum.STOP.getValue());
            jobDao.updateJobGroup(jobGroup);
        }
    }

    @Override
    public FlowResult continueGroup(long jobGroupId, boolean retry) {
        JobGroup jobGroup = jobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(jobGroupId);
            boolean needAction = false;
            switch (flowGuide.getStatus()) {
                case PAUSE: {
                    flowGuide.setStatus(JobGroupStatusEnum.EXECUTING);
                    jobGroup.setStatus(JobGroupStatusEnum.EXECUTING.getValue());
                    jobDao.updateJobGroup(jobGroup);
                    needAction = true;
                    break;
                }
                case EXECUTING: {
                    needAction = true;
                    break;
                }
            }
            if (needAction) {
                List<FlowNode> actionList = flowGuide.listContinueAction();
                return doActionList(jobGroup.getFlowGroupId(), jobGroup.getId(), jobGroup.getParameter(), actionList, retry);
            }
        }
        return null;
    }

    @Override
    public ActionResult scheduleAction(long actionId, String parameter, boolean retry) {
        ActionDesc desc = new ActionDesc();
        desc.setActionId(actionId);
        desc.setParameter(parameter);
        return scheduleAction(desc, retry);
    }

    public ActionResult scheduleAction(ActionDesc desc, boolean retry) {
        //转换参数
        Action action = actionDao.getAction(desc.getActionId());
        Parameter groupParam = new Parameter(desc.getGroupParameter());
        Parameter param = new Parameter(desc.getParameter());
        DynamicParamContext.getContext().parser(param);

        Set<String> groupParamSet = groupParam.listKeys();
        Set<String> paramSet = param.listKeys();
        for (String key : groupParamSet) {
            //如果任务的参数与任务组的参数一致，则参数的优先级当前任务>任务组
            if (!paramSet.contains(key)) {
                param.put(key, groupParam.getString(key));
            }
        }
        String parameter = param.toString();
        Job job = new Job();
        job.setJobGroupId(desc.getJobGroupId());
        job.setFlowGroupId(desc.getTriggerGroupId());
        job.setActionId(desc.getActionId());
        job.setCreateTime(System.currentTimeMillis());
        job.setStartTime(System.currentTimeMillis());
        job.setRetryCnt(0);
        job.setIndex(desc.getIndex());
        job.setRetryJobId(desc.getRetryJobId());
        job.setStatus(JobStatusEnum.SendOk.getValue());
        job.setParameter(parameter);
        jobDao.saveJob(job);

//        if (retry) {
//            RetryTask retryTask = new RetryTask();
//            retryTask.setActionDesc(desc);
//            retryTask.setRetryTime(System.currentTimeMillis());
//            retryTask.setJobId(job.getId());
//            retryTask.getActionDesc().setRetryJobId(job.getId());
//            //SchedulerContext.getContext().getRetryProcessor().put(retryTask);//加入重试队列
//        }

        JobReq req = new JobReq();
        req.setJobGroupId(desc.getJobGroupId());
        req.setActionId(desc.getActionId());
        req.setParameter(parameter);
        req.setId(job.getId());
        req.setIndex(desc.getIndex());
        req.setClassName(action.getClassName());
        boolean sendOk = false;
        try {
            IpAddr ip = context.getExecutorIpManager(action.getTag()).getIp();
            String accessToken = SecurityUtils.encrypt(context.getConfig().getAccessKey());
            ExecutorClient client = ExecutorClientFactory.create(ip, accessToken);
            Resp resp = client.schedule(req);
            if (resp.getCode() == ErrConstants.OK) {
                sendOk = true;
            }
        } catch (TException e) {
            //连接失败
        }
        if (!sendOk) {
            jobDao.updateJobStatus(job.getId(), JobStatusEnum.SendErr.getValue());
        }
        ActionResult result = new ActionResult();
        result.setActionDesc(desc);
        result.setStatus(sendOk ? JobStatusEnum.SendOk : JobStatusEnum.SendErr);
        return result;
    }

    /**
     * 触发回复任务
     *
     * @param jobId
     * @param code
     * @param msg*  @param retry
     * @return
     */
    @Override
    public boolean ackAction(long jobId, int code, String msg, boolean retry) {
        boolean result = false;
        Job job = jobDao.getJob(jobId);
        if (job != null) {
            //任务组
            if (job.getJobGroupId() != 0) {
                JobGroup jobGroup = jobDao.getJobGroup(job.getJobGroupId());
                //如果ack回应的服务器恰好是任务发起的服务器则直接处理并返回
                if (jobGroup.getStartServer().equals(context.getMyIp())) {
                    result = ackActionActual(jobId, code, msg, retry);
                } else {
                    //不是触发服务器，则发送给触发服务器
                    IpAddr ip = new IpAddr(jobGroup.getStartServer());
                    boolean startOk = sendAckWithIp(ip, jobId, code, msg);
                    //如果触发服务器发送失败，则交给master服务器
                    if (!startOk) {
                        if (context.isMaster()) {
                            result = ackMaster(jobId, code, msg, true);
                        } else {
                            IpAddr masterIp = new IpAddr(jobGroup.getStartServer());
                            result = sendAckMasterWithIp(masterIp, jobId, code, msg);
                        }
                    } else {
                        result = startOk;
                    }
                }
            } else {
                //如果不是任务组，则任何一台机器都可以接受并保存
                result = ackActionActual(jobId, code, msg, retry);
            }
        }
        return result;
    }

    @Override
    public boolean ackMaster(long jobId, int code, String msg, boolean retry) {
        Job job = jobDao.getJob(jobId);
        if (job != null) {
            //任务组
            if (job.getJobGroupId() != 0) {
                JobGroup jobGroup = jobDao.getJobGroup(job.getJobGroupId());
                jobGroup.setStartServer(context.getMyIp().toString());
                jobDao.updateJobGroup(jobGroup);
                return ackActionActual(jobId, code, msg, retry);
            }
        }
        return false;
    }

    private boolean sendAckWithIp(IpAddr ip, long jobId, int code, String msg) {
        SchedulerClient client = SchedulerClientFactory.createScheduler(ip, context.getConfig().getAccessKey());
        AckReq req = new AckReq();
        req.setJobId(jobId);
        req.setCode(code);
        req.setMsg(msg);
        Resp resp = client.ack(req);
        return resp.getCode() == ErrConstants.OK ? true : false;
    }

    private boolean sendAckMasterWithIp(IpAddr ip, long jobId, int code, String msg) {
        SchedulerClient client = SchedulerClientFactory.createScheduler(ip, context.getConfig().getAccessKey());
        AckReq req = new AckReq();
        req.setJobId(jobId);
        req.setCode(code);
        req.setMsg(msg);
        Resp resp = client.ackMaster(req);
        return resp.getCode() == ErrConstants.OK ? true : false;
    }


    private boolean ackActionActual(long jobId, int code, String msg, boolean retry) {
        boolean jobOk = code == ErrConstants.OK ? true : false;
        Job job = jobDao.getJob(jobId);
        if (job != null) {
            job.setEndTime(System.currentTimeMillis());//更新任务结束时间
            if (job.getJobGroupId() != 0) {
                FlowGuide flowGuide = flowMapHandle.getFlowGuide(job.getJobGroupId());
                if (jobOk) {
                    flowGuide.updateNodeOk(job.getIndex(), jobOk);
                    job.setStatus(JobStatusEnum.ExecuteOk.getValue());
                    //加入判断，当任务暂停的时候
                    if (flowGuide.getStatus() != JobGroupStatusEnum.PAUSE
                            && flowGuide.getStatus() != JobGroupStatusEnum.STOP) {
                        JobGroup jobGroup = jobDao.getJobGroup(job.getJobGroupId());
                        if (flowGuide.isFinish()) { //如果任务组已完成
                            jobGroup.setStatus(JobGroupStatusEnum.FINISH.getValue());
                            jobGroup.setEndTime(System.currentTimeMillis());
                            jobDao.updateJobGroup(jobGroup);
                        } else {
                            List<FlowNode> needActionList = flowGuide.listNeedAction(job.getIndex());
                            doActionList(job.getFlowGroupId(), job.getJobGroupId(), jobGroup.getParameter(), needActionList, retry);
                        }
                    }
                } else {
                    job.setStatus(JobStatusEnum.ExecuteErr.getValue());
                }
            } else {
                job.setStatus(jobOk ? JobStatusEnum.ExecuteOk.getValue() : JobStatusEnum.ExecuteErr.getValue());
            }
        }
        jobDao.updateJob(job);
        return true;
    }


    public JobGroup getJobGroup(long id) {
        return jobDao.getJobGroup(id);
    }
}
