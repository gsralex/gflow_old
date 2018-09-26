package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import com.gsralex.gflow.core.domain.GFlowTrigger;
import com.gsralex.gflow.core.flow.FlowGuide;
import com.gsralex.gflow.core.flow.FlowGuideMap;
import com.gsralex.gflow.scheduler.sql.ConfigDao;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class FlowMapHandle {


    private FlowGuideMap flowGuideMap;
    private ConfigDao configDao;
    private FlowJobDao flowJobDao;



    public FlowGuide initGroup(long jobGroupId, long triggerGroupId) {
        List<GFlowTrigger> triggerList = configDao.getTriggerList(triggerGroupId);
        FlowGuide flowGuide = new FlowGuide(triggerGroupId, triggerList);
        flowGuideMap.putFlowMap(jobGroupId, flowGuide);
        return flowGuide;
    }

    public FlowGuide getFlowGuide(long jobGroupId) {
        FlowGuide flowGuide = flowGuideMap.getFlowMap(jobGroupId);
        if (flowGuide == null) {
            GFlowJobGroup jobGroup = flowJobDao.getJobGroup(jobGroupId);
            if (jobGroup != null) {
                List<GFlowJob> jobList = flowJobDao.listJob(jobGroupId);
                List<GFlowTrigger> triggerList = configDao.getTriggerList(jobGroup.getTriggerGroupId());
                flowGuide = new FlowGuide(jobGroup.getTriggerGroupId(), triggerList, jobList);
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
