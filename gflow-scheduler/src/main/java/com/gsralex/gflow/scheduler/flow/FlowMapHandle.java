package com.gsralex.gflow.scheduler.flow;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.ScheduleContext;
import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import com.gsralex.gflow.core.domain.GFlowTrigger;
import com.gsralex.gflow.core.flow.FlowGuide;
import com.gsralex.gflow.scheduler.dao.ConfigDao;
import com.gsralex.gflow.scheduler.dao.FlowJobDao;
import com.gsralex.gflow.scheduler.dao.impl.ConfigDaoImpl;
import com.gsralex.gflow.scheduler.dao.impl.FlowJobDaoImpl;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class FlowMapHandle {


    private ScheduleContext scheduleContext;
    private ConfigDao configDao;
    private FlowJobDao flowJobDao;

    public FlowMapHandle(GFlowContext context) {
        this.configDao = new ConfigDaoImpl(context);
        this.flowJobDao = new FlowJobDaoImpl(context);
        this.scheduleContext = context.getScheduleContext();
    }


    public FlowGuide initGroup(long jobGroupId, long triggerGroupId) {
        List<GFlowTrigger> triggerList = configDao.getTriggerList(triggerGroupId);
        FlowGuide flowGuide = new FlowGuide(triggerGroupId, triggerList);
        scheduleContext.putFlowMap(jobGroupId, flowGuide);
        return flowGuide;
    }

    public FlowGuide getFlowGuide(long jobGroupId) {
        FlowGuide flowGuide = scheduleContext.getFlowMap(jobGroupId);
        if (flowGuide == null) {
            GFlowJobGroup jobGroup = flowJobDao.getJobGroup(jobGroupId);
            if (jobGroup != null) {
                List<GFlowJob> jobList = flowJobDao.listJob(jobGroupId);
                List<GFlowTrigger> triggerList = configDao.getTriggerList(jobGroup.getTriggerGroupId());
                flowGuide = new FlowGuide(jobGroup.getTriggerGroupId(), triggerList, jobList);
                scheduleContext.putFlowMap(jobGroup.getId(), flowGuide);

            }
        }
        return null;
    }

}
