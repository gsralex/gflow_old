package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.domain.GFlowAction;
import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import com.gsralex.gflow.core.enums.JobGroupStatusEnum;
import com.gsralex.gflow.core.enums.JobStatusEnum;
import com.gsralex.gflow.core.flow.FlowGuide;
import com.gsralex.gflow.core.flow.FlowNode;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.scheduler.sql.ConfigDao;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import com.gsralex.gflow.scheduler.thrift.TRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实际调度执行者
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



    public List<ScheduleResult> scheduleGroup(long triggerGroupId, Parameter parameter, long executeConfigId) {
        List<ScheduleResult> r = new ArrayList<>();
        GFlowJobGroup jobGroup = new GFlowJobGroup();
        jobGroup.setStartTime(DtUtils.getUnixTime());
        jobGroup.setCreateTime(DtUtils.getUnixTime());
        jobGroup.setStatus(JobGroupStatusEnum.EXECUTING.getValue());
        jobGroup.setTriggerGroupId(triggerGroupId);
        jobGroup.setDate(DtUtils.getBizDate());
        jobGroup.setExecuteConfigId(executeConfigId);
        flowJobDao.saveJobGroup(jobGroup);

        long jobGroupId = jobGroup.getId();
        FlowGuide flowGuide = flowMapHandle.initGroup(jobGroupId, triggerGroupId);
        List<FlowNode> rootList = flowGuide.listRoot();
        for (FlowNode node : rootList) {
            ScheduleResult result = scheduleAction(jobGroup.getId(), triggerGroupId, node.getActionId(), node.getIndex(), parameter);
            r.add(result);
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

    public List<ScheduleResult> continueGroup(long jobGroupId) {
        List<ScheduleResult> r = new ArrayList<>();
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
                for (FlowNode flowNode : actionList) {
                    ScheduleResult result = scheduleAction(jobGroupId, jobGroup.getTriggerGroupId(), flowNode.getActionId(),
                            flowNode.getIndex(), new Parameter());
                    r.add(result);
                }
            }
        }
        return r;
    }

    public void scheduleAction(long actionId, Parameter parameter) {
        scheduleAction(0, 0, actionId, 0, parameter);
    }

    public ScheduleResult scheduleAction(long jobGroupId, long groupId, long actionId, int index, Parameter parameter) {
        GFlowAction action = configDao.getAction(actionId);
        GFlowJob job = new GFlowJob();
        job.setJobGroupId(jobGroupId);
        job.setTriggerGroupId(groupId);
        job.setActionId(actionId);
        job.setCreateTime(DtUtils.getUnixTime());
        job.setStartTime(DtUtils.getUnixTime());
        job.setRetryCnt(0);
        job.setIndex(index);
        flowJobDao.saveJob(job);
        TJobDesc jobDesc = new TJobDesc();
        jobDesc.setJobGroupId(jobGroupId);
        jobDesc.setActionId(actionId);
        jobDesc.setParameter(parameter.toString());
        jobDesc.setId(job.getId());
        jobDesc.setIndex(index);
        jobDesc.setClassName(action.getClassName());
        boolean sendOk = false;
        if (rpcClient.schedule(jobDesc).isOk()) {
            job.setStatus(JobStatusEnum.SendOk.getValue());
            sendOk = true;
        } else {
            job.setStatus(JobStatusEnum.SendErr.getValue());
        }

        ScheduleResult result = new ScheduleResult();
        result.setActionId(actionId);
        result.setGroupId(groupId);
        result.setJobGroupId(jobGroupId);
        result.setStatus(sendOk ? JobStatusEnum.SendOk : JobStatusEnum.SendErr);
        result.setIndex(index);
        result.setJobId(job.getId());
        return result;
    }

    public List<ScheduleResult> actionAck(long jobId, boolean jobOk) {
        List<ScheduleResult> r = new ArrayList<>();
        GFlowJob job = flowJobDao.getJob(jobId);
        if (job != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(job.getJobGroupId());
            if (jobOk) {
                flowGuide.updateNodeOk(job.getIndex(), jobOk);
                job.setStatus(JobStatusEnum.ExecuteOk.getValue());
                if (flowGuide.getStatus() == JobGroupStatusEnum.EXECUTING) {
                    List<FlowNode> needActionList = flowGuide.listNeedAction(job.getIndex());
                    for (FlowNode action : needActionList) {
                        ScheduleResult result = scheduleAction(job.getJobGroupId(), job.getTriggerGroupId(), action.getActionId(),
                                action.getIndex(), new Parameter());
                        r.add(result);
                    }
                }
            }
        } else {
            job.setStatus(JobStatusEnum.ExecuteErr.getValue());
        }
        flowJobDao.updateJob(job);
        return r;
    }
}
