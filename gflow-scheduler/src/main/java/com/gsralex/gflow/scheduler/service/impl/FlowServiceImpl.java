package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.scheduler.dao.ConfigDao;
import com.gsralex.gflow.scheduler.dao.FlowJobDao;
import com.gsralex.gflow.scheduler.domain.flow.GFlowJobGroup;
import com.gsralex.gflow.scheduler.domain.flow.GFlowTrigger;
import com.gsralex.gflow.scheduler.domain.flow.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.rpc.JobDesc;
import com.gsralex.gflow.scheduler.rpc.RpcClient;
import com.gsralex.gflow.scheduler.service.FlowService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public class FlowServiceImpl implements FlowService {

    private ConfigDao configDao;

    private FlowJobDao flowJobDao;

    private RpcClient rpcClient;

    @Override
    public void startGroup(long triggerGroupId, String parameter) {
        List<Long> actionIdList = new ArrayList<>();
        List<GFlowTrigger> triggerList = configDao.getTriggerList(triggerGroupId);
        for (GFlowTrigger trigger : triggerList) {
            if (trigger.getTriggerActionId() == 0) {
                actionIdList.add(trigger.getActionId());
                JobDesc jobDesc = new JobDesc();
                jobDesc.setActionId(trigger.getActionId());
                jobDesc.setJobGroupId(trigger.getActionGroupId());
                jobDesc.setParameter(parameter);
                rpcClient.schedule(jobDesc);
            }
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
        JobDesc jobDesc = new JobDesc();
        jobDesc.setActionId(actionId);
        jobDesc.setJobGroupId(0);
        jobDesc.setParameter(parameter);
        rpcClient.schedule(jobDesc);
    }

    @Override
    public void actionAck(long triggerGroupId, long actionId, boolean jobOk) {

    }
}
