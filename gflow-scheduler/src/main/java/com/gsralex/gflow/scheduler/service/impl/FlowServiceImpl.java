package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.scheduler.dao.ConfigDao;
import com.gsralex.gflow.scheduler.dao.FlowJobDao;
import com.gsralex.gflow.scheduler.dao.impl.ConfigDaoImpl;
import com.gsralex.gflow.scheduler.dao.impl.FlowJobDaoImpl;
import com.gsralex.gflow.scheduler.domain.flow.GFlowJobGroup;
import com.gsralex.gflow.scheduler.domain.flow.GFlowTrigger;
import com.gsralex.gflow.scheduler.domain.flow.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.domain.flow.JobStatusEnum;
import com.gsralex.gflow.scheduler.thrift.JobDesc;
import com.gsralex.gflow.scheduler.thrift.RpcClient;
import com.gsralex.gflow.scheduler.service.FlowService;
import com.gsralex.gflow.scheduler.util.DtUtils;

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

    public FlowServiceImpl(GFlowContext context){
        configDao=new ConfigDaoImpl(context);
        flowJobDao=new FlowJobDaoImpl(context);

    }

    @Override
    public void startGroup(long triggerGroupId, String parameter,long executeConfigId) {
        List<Long> actionIdList = new ArrayList<>();
        List<GFlowTrigger> triggerList = configDao.getTriggerList(triggerGroupId);
        for (GFlowTrigger trigger : triggerList) {
            if(trigger.getTriggerGroupId()==triggerGroupId) {
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

        GFlowJobGroup jobGroup=new GFlowJobGroup();
        jobGroup.setStartTime(DtUtils.getUnixTime());
        jobGroup.setCreateTime(DtUtils.getUnixTime());
        jobGroup.setStatus(JobStatusEnum.Start.getValue());
        jobGroup.setTriggerGroupId(triggerGroupId);
        jobGroup.setDate(DtUtils.getBizDate());
        flowJobDao.saveJobGroup(jobGroup);

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
