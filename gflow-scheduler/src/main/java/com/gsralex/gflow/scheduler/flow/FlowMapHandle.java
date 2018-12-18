package com.gsralex.gflow.scheduler.flow;

import com.gsralex.gflow.scheduler.domain.Flow;
import com.gsralex.gflow.scheduler.domain.FlowDirect;
import com.gsralex.gflow.scheduler.domain.Job;
import com.gsralex.gflow.scheduler.domain.JobGroup;
import com.gsralex.gflow.scheduler.enums.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.sql.ActionDao;
import com.gsralex.gflow.scheduler.sql.FlowDao;
import com.gsralex.gflow.scheduler.sql.JobDao;
import com.gsralex.gflow.scheduler.sql.impl.ActionDaoImpl;
import com.gsralex.gflow.scheduler.sql.impl.FlowDaoImpl;
import com.gsralex.gflow.scheduler.sql.impl.JobDaoImpl;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/19
 */

public class FlowMapHandle {

    private ActionDao actionDao;
    private JobDao jobDao;
    private FlowDao flowDao;

    private SchedulerContext context;

    public FlowMapHandle(SchedulerContext context) {
        actionDao = new ActionDaoImpl(context.getJdbcUtils());
        jobDao = new JobDaoImpl(context.getJdbcUtils());
        flowDao = new FlowDaoImpl(context.getJdbcUtils());
        this.context = context;
    }


    public FlowGuide initGroup(long jobGroupId, long flowGroupId) {
        List<Flow> flowList = flowDao.listFlow(flowGroupId);
        List<FlowDirect> directList = flowDao.listFlowDirect(flowGroupId);
        FlowGuide flowGuide = new FlowGuide(jobGroupId, flowList, directList, JobGroupStatusEnum.EXECUTING);
        FlowGuideMap flowGuideMap = context.getFlowGuideMap();
        flowGuideMap.putFlowMap(jobGroupId, flowGuide);
        return flowGuide;
    }

    public FlowGuide getFlowGuide(long jobGroupId) {
        FlowGuideMap flowGuideMap = context.getFlowGuideMap();
        FlowGuide flowGuide = flowGuideMap.getFlowMap(jobGroupId);
        if (flowGuide == null) {
            JobGroup jobGroup = jobDao.getJobGroup(jobGroupId);
            if (jobGroup != null) {
                List<Job> jobList = jobDao.listJob(jobGroupId);
                List<Flow> flowList = flowDao.listFlow(jobGroupId);
                List<FlowDirect> directList = flowDao.listFlowDirect(jobGroupId);
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
