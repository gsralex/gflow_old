package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thrift.gen.TJobDesc;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.scheduler.dao.ConfigDao;
import com.gsralex.gflow.scheduler.dao.FlowJobDao;
import com.gsralex.gflow.scheduler.dao.impl.ConfigDaoImpl;
import com.gsralex.gflow.scheduler.dao.impl.FlowJobDaoImpl;
import com.gsralex.gflow.scheduler.domain.flow.*;
import com.gsralex.gflow.scheduler.service.FlowService;
import com.gsralex.gflow.scheduler.thrift.TRpcClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public class FlowServiceImpl implements FlowService {

    private ConfigDao configDao;

    private FlowJobDao flowJobDao;

    private TRpcClient rpcClient;

    public FlowServiceImpl(GFlowContext context) {
        configDao = new ConfigDaoImpl(context);
        flowJobDao = new FlowJobDaoImpl(context);
        rpcClient = new TRpcClient(context);
    }

    @Override
    public void startGroup(long triggerGroupId, String parameter, long executeConfigId) {
        GFlowJobGroup jobGroup = new GFlowJobGroup();
        jobGroup.setStartTime(DtUtils.getUnixTime());
        jobGroup.setCreateTime(DtUtils.getUnixTime());
        jobGroup.setStatus(JobGroupStatusEnum.EXECUTING.getValue());
        jobGroup.setTriggerGroupId(triggerGroupId);
        jobGroup.setDate(DtUtils.getBizDate());
        flowJobDao.saveJobGroup(jobGroup);
        List<GFlowTrigger> triggerList = configDao.getTriggerList(triggerGroupId);
        for (GFlowTrigger trigger : triggerList) {
            if (trigger.getTriggerGroupId() != triggerGroupId) {
                continue;
            }
            if (trigger.getTriggerActionId() != 0) {
                continue;
            }
            startAction(jobGroup.getId(), triggerGroupId, trigger.getActionId(), "");
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
        flowJobDao.saveJob(job);
        TJobDesc jobDesc = new TJobDesc();
        jobDesc.setJobGroupId(jobGroupId);
        jobDesc.setActionId(actionId);
        jobDesc.setParameter(gflowParameter.toString());
        jobDesc.setJobId(job.getId());
        if (rpcClient.schedule(jobDesc).isOk()) {
            job.setStatus(JobStatusEnum.SENDOK.getValue());
        } else {
            job.setStatus(JobStatusEnum.SENDERR.getValue());
        }
        flowJobDao.updateJob(job);

    }

    @Override
    public void actionAck(long jobId, boolean jobOk) {
        GFlowJob job = flowJobDao.getJob(jobId);
        if (job != null) {
            long jobGroupId = job.getJobGroupId();
            long triggerGroupId = job.getTriggerGroupId();
            long actionId = job.getActionId();
            if (jobOk) {
                List<GFlowTrigger> list = configDao.getNeedActionList(triggerGroupId, actionId);
                for (GFlowTrigger item : list) {
                    startAction(jobGroupId, triggerGroupId, item.getActionId(), "");
                }
            } else {
                //加入重试队列
            }
        } else {

        }


    }
}
