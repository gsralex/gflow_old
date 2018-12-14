package com.gsralex.gflow.scheduler.flow;

import com.gsralex.gflow.scheduler.domain.Flow;
import com.gsralex.gflow.scheduler.domain.FlowDirect;
import com.gsralex.gflow.scheduler.domain.Job;
import com.gsralex.gflow.scheduler.domain.JobGroup;
import com.gsralex.gflow.scheduler.enums.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.sql.ConfigDao;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import com.gsralex.gflow.scheduler.sql.impl.ConfigDaoImpl;
import com.gsralex.gflow.scheduler.sql.impl.FlowJobDaoImpl;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/19
 */

public class FlowMapHandle {

    private ConfigDao configDao;
    private FlowJobDao flowJobDao;

    private SchedulerContext context;

    public FlowMapHandle(SchedulerContext context) {
        configDao = new ConfigDaoImpl(context.getJdbcUtils());
        flowJobDao = new FlowJobDaoImpl(context.getJdbcUtils());
        this.context = context;
    }


    public FlowGuide initGroup(long jobGroupId, long flowGroupId) {
        List<Flow> flowList = configDao.listFlow(flowGroupId);
        List<FlowDirect> directList = configDao.listFlowDirect(flowGroupId);
        FlowGuide flowGuide = new FlowGuide(jobGroupId, flowList, directList, JobGroupStatusEnum.EXECUTING);
        FlowGuideMap flowGuideMap = context.getFlowGuideMap();
        flowGuideMap.putFlowMap(jobGroupId, flowGuide);
        return flowGuide;
    }

    public FlowGuide getFlowGuide(long jobGroupId) {
        FlowGuideMap flowGuideMap = context.getFlowGuideMap();
        FlowGuide flowGuide = flowGuideMap.getFlowMap(jobGroupId);
        if (flowGuide == null) {
            JobGroup jobGroup = flowJobDao.getJobGroup(jobGroupId);
            if (jobGroup != null) {
                List<Job> jobList = flowJobDao.listJob(jobGroupId);
                List<Flow> flowList = configDao.listFlow(jobGroupId);
                List<FlowDirect> directList = configDao.listFlowDirect(jobGroupId);
                JobGroupStatusEnum status = JobGroupStatusEnum.valueOf(jobGroup.getStatus());
                flowGuide = new FlowGuide(jobGroupId, flowList, directList, jobList, status);
                flowGuideMap.putFlowMap(jobGroup.getId(), flowGuide);
                return flowGuide;
            } else {
                return null;
            }
        } else {
            return flowGuide;
        }
    }

}
