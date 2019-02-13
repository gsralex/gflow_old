package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.executor.client.action.JobReq;
import com.gsralex.gflow.executor.client.action.Resp;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.dao.ActionDao;
import com.gsralex.gflow.scheduler.dao.JobDao;
import com.gsralex.gflow.scheduler.dao.impl.ActionDaoImpl;
import com.gsralex.gflow.scheduler.dao.impl.JobDaoImpl;
import com.gsralex.gflow.scheduler.domain.Action;
import com.gsralex.gflow.scheduler.domain.Job;
import com.gsralex.gflow.scheduler.domain.JobGroup;
import com.gsralex.gflow.scheduler.enums.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.enums.JobStatusEnum;
import com.gsralex.gflow.scheduler.flow.FlowGuide;
import com.gsralex.gflow.scheduler.flow.FlowMapHandle;
import com.gsralex.gflow.scheduler.flow.FlowNode;
import com.gsralex.gflow.scheduler.parameter.DynamicParamContext;
import com.gsralex.gflow.scheduler.retry.RetryTask;
import com.gsralex.gflow.scheduler.retry.RetryTaskProcess;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Set;

/**
 * 实际调度执行者
 *
 * @author gsralex
 * @version 2018/6/2
 */
public class SchedulerService {

    private ActionDao actionDao;
    private JobDao jobDao;
    private FlowMapHandle flowMapHandle;
    private SchedulerContext context;

    public SchedulerService(SchedulerContext context) {
        this.context = context;
        actionDao = new ActionDaoImpl(context.getJdbcUtils());
        jobDao = new JobDaoImpl(context.getJdbcUtils());
        flowMapHandle = new FlowMapHandle(context);

    }


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

    public void stopGroup(long jobGroupId) {
        JobGroup jobGroup = jobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(jobGroupId);
            flowGuide.setStatus(JobGroupStatusEnum.STOP);
            jobGroup.setStatus(JobGroupStatusEnum.STOP.getValue());
            jobDao.updateJobGroup(jobGroup);
        }
    }

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
            if (!paramSet.contains(key)) { //如果任务的参数与任务组的参数一致，则参数的优先级当前任务>任务组
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
            Resp resp = context.getExecutorClient().schedule(req);
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

    public FlowResult ackAction(long jobId, boolean jobOk, boolean retry) {
        FlowResult r = new FlowResult();
        Job job = jobDao.getJob(jobId);
        if (job != null) {
            if (job.getJobGroupId() != 0) { //任务组
                JobGroup jobGroup = jobDao.getJobGroup(job.getJobGroupId());
                if (jobGroup.getScheduleServer().equals(context.getMyServer())) {
                    ackActionActual(jobId, jobOk, retry);
                } else {
                    RetryTaskProcess process = new RetryTaskProcess();
                    boolean ok = process.doProcess(new RetryTask() {
                        @Override
                        public boolean doAction() {
//                            IpAddress ipAddress = new IpAddress(jobGroup.getScheduleServer());
//                            SchedulerClient client = new SchedulerClient();
//                            return client.ackAction(jobId, jobOk);
                            return false;
                        }
                    });

                    if (!ok) {
                        //sendMaster
                    }
                    //TODO:发送给指定ip
                    //TODO:发送失败则交给master来处理
                }
            } else {
                ackActionActual(jobId, jobOk, retry);
            }
        }
        return r;
    }


    public FlowResult ackActionActual(long jobId, boolean jobOk, boolean retry) {
        FlowResult r = new FlowResult();
        Job job = jobDao.getJob(jobId);
        if (job != null) {
            job.setEndTime(System.currentTimeMillis());//更新任务结束时间
            if (job.getJobGroupId() != 0) {
                FlowGuide flowGuide = flowMapHandle.getFlowGuide(job.getJobGroupId());
                if (retry) {
                    //SchedulerContext.getContext().getRetryProcessor().mark(jobId, job.getRetryJobId(), jobOk);
                }
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
        return r;
    }


    public JobGroup getJobGroup(long id) {
        return jobDao.getJobGroup(id);
    }
}