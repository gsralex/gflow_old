package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.domain.GFlowAction;
import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import com.gsralex.gflow.core.enums.JobGroupStatusEnum;
import com.gsralex.gflow.core.enums.JobStatusEnum;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.flow.FlowGuide;
import com.gsralex.gflow.scheduler.flow.FlowNode;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.scheduler.retry.RetryTask;
import com.gsralex.gflow.scheduler.sql.ConfigDao;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import com.gsralex.gflow.scheduler.thrift.TRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实际调度执行者
 *
 * @author gsralex
 * @version 2018/6/2
 */
@Service
public class ScheduleActualHanle {

    @Autowired
    private ConfigDao configDao;
    @Autowired
    private FlowJobDao flowJobDao;
    @Autowired
    private TRpcClient rpcClient;
    @Autowired
    private FlowMapHandle flowMapHandle;


    public FlowResult scheduleGroup(long triggerGroupId, String parameter, long executeConfigId, boolean retry) {
        GFlowJobGroup jobGroup = new GFlowJobGroup();
        jobGroup.setStartTime(System.currentTimeMillis());
        jobGroup.setCreateTime(System.currentTimeMillis());
        jobGroup.setStatus(JobGroupStatusEnum.EXECUTING.getValue());
        jobGroup.setTriggerGroupId(triggerGroupId);
        jobGroup.setDate(DtUtils.getBizDate());
        jobGroup.setExecuteConfigId(executeConfigId);
        flowJobDao.saveJobGroup(jobGroup);

        FlowGuide flowGuide = flowMapHandle.initGroup(jobGroup.getId(), triggerGroupId);
        List<FlowNode> rootList = flowGuide.listRoot();
        return doActionList(triggerGroupId, jobGroup.getId(), parameter, rootList, retry);
    }

    private FlowResult doActionList(long triggerGroupId, long jobGroupId, String parameter, List<FlowNode> nodeList, boolean retry) {
        FlowResult r = new FlowResult();
        for (FlowNode node : nodeList) {
            ActionDesc desc = new ActionDesc();
            desc.setTriggerGroupId(triggerGroupId);
            desc.setJobGroupId(jobGroupId);
            desc.setActionId(node.getActionId());
            desc.setIndex(node.getIndex());
            desc.setParameter(parameter);
            ActionResult result = scheduleAction(desc, retry);
            r.getResults().add(result);
        }
        return r;
    }


    public void pauseGroup(long jobGroupId) {
        GFlowJobGroup jobGroup = flowJobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(jobGroupId);
            flowGuide.setStatus(JobGroupStatusEnum.PAUSE);
            //更新数据库
            jobGroup.setStatus(JobGroupStatusEnum.PAUSE.getValue());
            flowJobDao.updateJobGroup(jobGroup);
        }
    }

    public FlowResult continueGroup(long jobGroupId, boolean retry) {
        GFlowJobGroup jobGroup = flowJobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(jobGroupId);
            boolean needAction = false;
            switch (flowGuide.getStatus()) {
                case PAUSE: {
                    flowGuide.setStatus(JobGroupStatusEnum.EXECUTING);
                    jobGroup.setStatus(JobGroupStatusEnum.EXECUTING.getValue());
                    flowJobDao.updateJobGroup(jobGroup);
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
                return doActionList(jobGroup.getTriggerGroupId(), jobGroup.getId(), "", actionList, retry);
            }
        }
        return null;
    }

    public void scheduleAction(long actionId, String parameter, boolean retry) {
        ActionDesc desc = new ActionDesc();
        desc.setActionId(actionId);
        desc.setParameter(parameter);
        scheduleAction(desc, retry);
    }

    public ActionResult scheduleAction(ActionDesc desc, boolean retry) {
        GFlowAction action = configDao.getAction(desc.getActionId());
        GFlowJob job = new GFlowJob();
        job.setJobGroupId(desc.getJobGroupId());
        job.setTriggerGroupId(desc.getTriggerGroupId());
        job.setActionId(desc.getActionId());
        job.setCreateTime(System.currentTimeMillis());
        job.setStartTime(System.currentTimeMillis());
        job.setRetryCnt(0);
        job.setIndex(desc.getIndex());
        job.setRetryJobId(desc.getRetryJobId());
        job.setStatus(JobStatusEnum.SendOk.getValue());
        flowJobDao.saveJob(job);

        if (retry) {
            RetryTask retryTask = new RetryTask();
            retryTask.setActionDesc(desc);
            retryTask.setRetryTime(System.currentTimeMillis());
            retryTask.setJobId(job.getId());
            retryTask.getActionDesc().setRetryJobId(job.getId());
            SchedulerContext.getContext().getRetryProcessor().put(retryTask);//加入重试队列
        }

        TJobDesc jobDesc = new TJobDesc();
        jobDesc.setJobGroupId(desc.getJobGroupId());
        jobDesc.setActionId(desc.getActionId());
        jobDesc.setParameter(desc.getParameter());
        jobDesc.setId(job.getId());
        jobDesc.setIndex(desc.getIndex());
        jobDesc.setClassName(action.getClassName());
        boolean sendOk = false;
        if (rpcClient.schedule(jobDesc).getCode() == ErrConstants.OK) {
            sendOk = true;
        } else {
            flowJobDao.updateJobStatus(job.getId(), JobStatusEnum.SendErr.getValue());
        }
        ActionResult result = new ActionResult();
        result.setActionDesc(desc);
        result.setStatus(sendOk ? JobStatusEnum.SendOk : JobStatusEnum.SendErr);
        return result;
    }

    public FlowResult actionAck(long jobId, boolean jobOk, boolean retry) {
        FlowResult r = new FlowResult();
        GFlowJob job = flowJobDao.getJob(jobId);
        if (job != null) {
            job.setEndTime(System.currentTimeMillis());//更新任务结束时间
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(job.getJobGroupId());

            if (retry) {
                SchedulerContext.getContext().getRetryProcessor().mark(jobId, job.getRetryJobId(), jobOk);
            }
            if (jobOk) {
                flowGuide.updateNodeOk(job.getIndex(), jobOk);
                job.setStatus(JobStatusEnum.ExecuteOk.getValue());
                //加入判断，当任务暂停的时候
                if (flowGuide.getStatus() != JobGroupStatusEnum.PAUSE
                        && flowGuide.getStatus() != JobGroupStatusEnum.STOP) {
                    List<FlowNode> needActionList = flowGuide.listNeedAction(job.getIndex());
                    doActionList(job.getTriggerGroupId(), job.getJobGroupId(), "", needActionList, retry);
                }
            } else {
                job.setStatus(JobStatusEnum.ExecuteErr.getValue());
            }
        }
        flowJobDao.updateJob(job);
        return r;
    }
}
