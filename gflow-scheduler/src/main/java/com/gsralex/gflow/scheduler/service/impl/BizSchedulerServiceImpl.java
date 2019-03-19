package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.domain.ActionPo;
import com.gsralex.gflow.core.domain.JobGroupPo;
import com.gsralex.gflow.core.domain.JobPo;
import com.gsralex.gflow.core.enums.JobGroupStatus;
import com.gsralex.gflow.core.enums.JobStatus;
import com.gsralex.gflow.core.rpc.client.RpcClientFactory;
import com.gsralex.gflow.core.rpc.client.RpcClientManager;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.executor.client.ExecutorService;
import com.gsralex.gflow.executor.client.JobReq;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.dao.ActionDao;
import com.gsralex.gflow.scheduler.dao.JobDao;
import com.gsralex.gflow.scheduler.flow.FlowGuide;
import com.gsralex.gflow.scheduler.flow.FlowMapHandle;
import com.gsralex.gflow.scheduler.flow.FlowNode;
import com.gsralex.gflow.scheduler.parameter.DynamicParamContext;
import com.gsralex.gflow.scheduler.schedule.ActionDesc;
import com.gsralex.gflow.scheduler.schedule.ActionResult;
import com.gsralex.gflow.scheduler.schedule.FlowResult;
import com.gsralex.gflow.scheduler.service.BizSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class BizSchedulerServiceImpl implements BizSchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(BizSchedulerServiceImpl.class);

    @Autowired
    private ActionDao actionDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private FlowMapHandle flowMapHandle;
    private SchedulerContext context = SchedulerContext.getInstance();

    @Override
    public FlowResult scheduleGroup(long triggerGroupId, String parameter, long timerConfigId, boolean retry) {
        Parameter param = new Parameter(parameter);
        DynamicParamContext.getContext().parser(param);

        JobGroupPo jobGroup = new JobGroupPo();
        jobGroup.setStartTime(System.currentTimeMillis());
        jobGroup.setCreateTime(System.currentTimeMillis());
        jobGroup.setStatus(JobGroupStatus.EXECUTING.getValue());
        jobGroup.setFlowGroupId(triggerGroupId);
        jobGroup.setDate(DtUtils.getBizDate());
        jobGroup.setTimerConfigId(timerConfigId);
        jobGroup.setParameter(param.toString());
        jobGroup.setStartServer(context.getMyIp().toString());
        jobDao.saveJobGroup(jobGroup);

        FlowGuide flowGuide = flowMapHandle.initGroup(jobGroup.getId(), triggerGroupId);
        List<FlowNode> rootList = flowGuide.listRoot();
        FlowResult result = doActionList(triggerGroupId, jobGroup.getId(), parameter, rootList, retry);
        result.setJobGroupId(jobGroup.getId());
        return result;
    }

    @Override
    public void pauseGroup(long jobGroupId) {
        JobGroupPo jobGroup = jobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(jobGroupId);
            flowGuide.setStatus(JobGroupStatus.PAUSE);
            //更新数据库
            jobGroup.setStatus(JobGroupStatus.PAUSE.getValue());
            jobDao.updateJobGroup(jobGroup);
        }
    }

    @Override
    public void stopGroup(long jobGroupId) {
        JobGroupPo jobGroup = jobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(jobGroupId);
            flowGuide.setStatus(JobGroupStatus.STOP);
            jobGroup.setStatus(JobGroupStatus.STOP.getValue());
            jobDao.updateJobGroup(jobGroup);
        }
    }

    @Override
    public FlowResult continueGroup(long jobGroupId, boolean retry) {
        JobGroupPo jobGroup = jobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(jobGroupId);
            boolean needAction = false;
            switch (flowGuide.getStatus()) {
                case PAUSE: {
                    flowGuide.setStatus(JobGroupStatus.EXECUTING);
                    jobGroup.setStatus(JobGroupStatus.EXECUTING.getValue());
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
        return null;
    }

    @Override
    public boolean ackAction(long jobId, boolean jobOk, String msg, boolean retry) {
        JobPo job = jobDao.getJob(jobId);
        if (job != null) {
            job.setEndTime(System.currentTimeMillis());//更新任务结束时间
            if (job.getJobGroupId() != 0) {
                FlowGuide flowGuide = flowMapHandle.getFlowGuide(job.getJobGroupId());
                if (jobOk) {
                    flowGuide.updateNodeOk(job.getIndex(), jobOk);
                    job.setStatus(JobStatus.ExecuteOk.getValue());
                    //加入判断，当任务暂停的时候
                    if (flowGuide.getStatus() != JobGroupStatus.PAUSE
                            && flowGuide.getStatus() != JobGroupStatus.STOP) {
                        JobGroupPo jobGroup = jobDao.getJobGroup(job.getJobGroupId());
                        if (flowGuide.isFinish()) { //如果任务组已完成
                            jobGroup.setStatus(JobGroupStatus.FINISH.getValue());
                            jobGroup.setEndTime(System.currentTimeMillis());
                            jobDao.updateJobGroup(jobGroup);
                        } else {
                            List<FlowNode> needActionList = flowGuide.listNeedAction(job.getIndex());
                            doActionList(job.getFlowGroupId(), job.getJobGroupId(), jobGroup.getParameter(), needActionList, retry);
                        }
                    }
                } else {
                    job.setStatus(JobStatus.ExecuteErr.getValue());
                }
            } else {
                job.setStatus(jobOk ? JobStatus.ExecuteOk.getValue() : JobStatus.ExecuteErr.getValue());
            }
        }
        jobDao.updateJob(job);
        return true;
    }

    @Override
    public boolean ackMaster(long jobId, boolean jobOk, String msg, boolean retry) {
        JobPo job = jobDao.getJob(jobId);
        if (job != null) {
            if (job.getJobGroupId() != 0) {
                JobGroupPo jobGroup = jobDao.getJobGroup(jobId);
                if (jobGroup != null) {
                    jobGroup.setStartServer(context.getMyIp().toString());
                    jobDao.updateJobGroup(jobGroup);
                    return ackAction(jobId, jobOk, msg, retry);
                }
            }
        }
        return false;
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

    public ActionResult scheduleAction(ActionDesc desc, boolean retry) {
        //转换参数
        ActionPo action = actionDao.getAction(desc.getActionId());
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
        JobPo job = new JobPo();
        job.setJobGroupId(desc.getJobGroupId());
        job.setFlowGroupId(desc.getTriggerGroupId());
        job.setActionId(desc.getActionId());
        job.setCreateTime(System.currentTimeMillis());
        job.setStartTime(System.currentTimeMillis());
        job.setRetryCnt(0);
        job.setIndex(desc.getIndex());
        job.setRetryJobId(desc.getRetryJobId());
        job.setStatus(JobStatus.Executing.getValue());
        job.setParameter(parameter);
        job.setScheduleServer(context.getMyIp().toString());
        jobDao.saveJob(job);
        boolean sendOk = sendActionToExecutor(job.getId(), action.getClassName(), desc.getParameter(), action.getTag());
//        job.setExecuterServer(ip.toString());
        if (retry) {
//            RetryTask retryTask = new RetryTask();
//            retryTask.setActionDesc(desc);
//            retryTask.setRetryTime(System.currentTimeMillis());
//            retryTask.setJobId(job.getId());
//            retryTask.getActionDesc().setRetryJobId(job.getId());
            //SchedulerContext.getContext().getRetryProcessor().put(retryTask);//加入重试队列
        }

        if (!sendOk) {
            jobDao.updateJobStatus(job.getId(), JobStatus.SendErr.getValue());
        }
        ActionResult result = new ActionResult();
        result.setActionDesc(desc);
        result.setStatus(sendOk ? JobStatus.Executing : JobStatus.SendErr);
        return result;
    }

    private boolean sendActionToExecutor(long jobId, String className, String parameter, String tag) {
        RpcClientManager rpcClientManager = context.getExecutorClientManager().getRpcClientManager(tag);
        ExecutorService service = RpcClientFactory.create(ExecutorService.class, rpcClientManager);
        JobReq req = new JobReq();
        req.setParameter(parameter);
        req.setJobId(jobId);
        req.setClassName(className);
        return service.scheduleAction(req);
    }

    @Override
    public JobPo getJob(long id) {
        return jobDao.getJob(id);
    }

    @Override
    public JobGroupPo getJobGroup(long id) {
        return jobDao.getJobGroup(id);
    }
}
