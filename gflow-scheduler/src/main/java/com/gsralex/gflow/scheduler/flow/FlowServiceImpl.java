package com.gsralex.gflow.scheduler.flow;

import com.gsralex.gflow.core.context.GFlowContext;
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
import com.gsralex.gflow.scheduler.FlowService;
import com.gsralex.gflow.scheduler.dao.ConfigDao;
import com.gsralex.gflow.scheduler.dao.FlowJobDao;
import com.gsralex.gflow.scheduler.dao.impl.ConfigDaoImpl;
import com.gsralex.gflow.scheduler.dao.impl.FlowJobDaoImpl;
import com.gsralex.gflow.scheduler.thrift.TRpcClient;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public class FlowServiceImpl implements FlowService {

    private ConfigDao configDao;
    private FlowJobDao flowJobDao;
    private TRpcClient rpcClient;
    private FlowMapHandle flowMapHandle;


    public FlowServiceImpl(GFlowContext context) {
        this.configDao = new ConfigDaoImpl(context);
        this.flowJobDao = new FlowJobDaoImpl(context);
        this.rpcClient = new TRpcClient(context);
        this.flowMapHandle = new FlowMapHandle(context);
    }

    @Override
    public void startGroup(long triggerGroupId, String parameter, long executeConfigId) {
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
            startAction(jobGroup.getId(), triggerGroupId, node.getActionId(), "");
        }
    }

    @Override
    public void pauseGroup(long jobGroupId) {
        GFlowJobGroup jobGroup = flowJobDao.getJobGroup(jobGroupId);
        if (jobGroup != null) {
            jobGroup.setStatus(JobGroupStatusEnum.PAUSE.getValue());
        }
        flowJobDao.updateJobGroup(jobGroup);
    }

    @Override
    public void startAction(long actionId, String parameter) {
        startAction(0, 0, actionId, parameter);
    }

    private void startAction(long jobGroupId, long triggerGroupId, long actionId, String parameter) {
        GFlowAction action = configDao.getAction(actionId);
        Parameter gflowParameter = new Parameter(parameter);
        gflowParameter.put("actionClass", action.getClassName());

        GFlowJob job = new GFlowJob();
        job.setJobGroupId(jobGroupId);
        job.setTriggerGroupId(triggerGroupId);
        job.setActionId(actionId);
        job.setCreateTime(DtUtils.getUnixTime());
        job.setStartTime(DtUtils.getUnixTime());
        job.setRetryCnt(0);
        flowJobDao.saveJob(job);
        TJobDesc jobDesc = new TJobDesc();
        jobDesc.setJobGroupId(jobGroupId);
        jobDesc.setActionId(actionId);
        jobDesc.setParameter(gflowParameter.toString());
        jobDesc.setJobId(job.getId());
        if (rpcClient.schedule(jobDesc).isOk()) {
            job.setStatus(JobStatusEnum.Executing.getValue());
        } else {
            job.setStatus(JobStatusEnum.SendErr.getValue());
        }
        flowJobDao.updateJob(job);

    }

    @Override
    public void actionAck(long jobId, boolean jobOk) {
        int retryCnt = 0;
        GFlowJob job = flowJobDao.getJob(jobId);
        if (job != null) {
            FlowGuide flowGuide = flowMapHandle.getFlowGuide(job.getJobGroupId());
            if (jobOk) {
                flowGuide.updateNodeOk(job.getIndex(), jobOk);
                List<FlowNode> needActionList = flowGuide.listNeedAction(job.getIndex());
                for (FlowNode action : needActionList) {
                    startAction(job.getJobGroupId(), job.getTriggerGroupId(), action.getActionId(), "");
                }
            } else {
                if (job.getRetryCnt() < retryCnt) {
                    startAction(job.getJobGroupId(), job.getTriggerGroupId(), job.getActionId(), "");
                    job.setRetryCnt(job.getRetryCnt() + 1);
                    flowJobDao.updateJob(job);
                }
            }
        }
    }
}
