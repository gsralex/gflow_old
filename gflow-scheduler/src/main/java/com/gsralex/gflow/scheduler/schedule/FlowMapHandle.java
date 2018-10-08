package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import com.gsralex.gflow.core.domain.GFlowTrigger;
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
        List<GFlowTrigger> triggerList = configDao.getTriggerList(triggerGroupId);
        FlowGuide flowGuide = new FlowGuide(triggerGroupId, triggerList, JobGroupStatusEnum.EXECUTING);
        FlowGuideMap flowGuideMap = SchedulerContext.getContext().getFlowGuideMap();
        flowGuideMap.putFlowMap(jobGroupId, flowGuide);
        return flowGuide;
    }

    public FlowGuide getFlowGuide(long jobGroupId) {
        FlowGuideMap flowGuideMap = SchedulerContext.getContext().getFlowGuideMap();
        FlowGuide flowGuide = flowGuideMap.getFlowMap(jobGroupId);
        if (flowGuide == null) {
            GFlowJobGroup jobGroup = flowJobDao.getJobGroup(jobGroupId);
            if (jobGroup != null) {
                List<GFlowJob> jobList = flowJobDao.listJob(jobGroupId);
                List<GFlowTrigger> triggerList = configDao.getTriggerList(jobGroup.getTriggerGroupId());
                JobGroupStatusEnum status = JobGroupStatusEnum.valueOf(jobGroup.getStatus());
                flowGuide = new FlowGuide(jobGroup.getTriggerGroupId(), triggerList, jobList, status);
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
