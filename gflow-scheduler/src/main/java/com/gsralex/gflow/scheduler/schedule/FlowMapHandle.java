package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.domain.Job;
import com.gsralex.gflow.core.domain.JobGroup;
import com.gsralex.gflow.core.domain.Flow;
import com.gsralex.gflow.core.enums.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.flow.FlowGuide;
import com.gsralex.gflow.scheduler.flow.FlowGuideMap;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.sql.ConfigDao;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/19
 */
@Service
public class FlowMapHandle {


    @Autowired
    private ConfigDao configDao;
    @Autowired
    private FlowJobDao flowJobDao;


    public FlowGuide initGroup(long jobGroupId, long triggerGroupId) {
        List<Flow> triggerList = configDao.getFlowList(triggerGroupId);
        FlowGuide flowGuide = new FlowGuide(triggerGroupId, triggerList, JobGroupStatusEnum.EXECUTING);
        FlowGuideMap flowGuideMap = SchedulerContext.getContext().getFlowGuideMap();
        flowGuideMap.putFlowMap(jobGroupId, flowGuide);
        return flowGuide;
    }

    public FlowGuide getFlowGuide(long jobGroupId) {
        FlowGuideMap flowGuideMap = SchedulerContext.getContext().getFlowGuideMap();
        FlowGuide flowGuide = flowGuideMap.getFlowMap(jobGroupId);
        if (flowGuide == null) {
            JobGroup jobGroup = flowJobDao.getJobGroup(jobGroupId);
            if (jobGroup != null) {
                List<Job> jobList = flowJobDao.listJob(jobGroupId);
                List<Flow> triggerList = configDao.getFlowList(jobGroup.getFlowGroupId());
                JobGroupStatusEnum status = JobGroupStatusEnum.valueOf(jobGroup.getStatus());
                flowGuide = new FlowGuide(jobGroup.getFlowGroupId(), triggerList, jobList, status);
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
