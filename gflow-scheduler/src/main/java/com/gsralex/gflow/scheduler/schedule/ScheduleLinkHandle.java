package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import com.gsralex.gflow.scheduler.sql.impl.FlowJobDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 调度链路
 *
 * @author gsralex
 * @version 2018/8/22
 */
public class ScheduleLinkHandle {

    private ScheduleActualHanle scheduleActualHanle;
    private FlowJobDao flowJobDao;


    private boolean retry;

    public ScheduleLinkHandle(SchedulerContext context) {
        retry = getRetry();

        flowJobDao = new FlowJobDaoImpl(context.getJdbcUtils());

        scheduleActualHanle = new ScheduleActualHanle(context);
    }

    public void scheduleGroup(long groupId, String parameter, long executeConfigId) {
        scheduleActualHanle.scheduleGroup(groupId, parameter, executeConfigId, retry);
    }

    public void pauseGroup(long jobGroupId) {
        scheduleActualHanle.pauseGroup(jobGroupId);
    }

    public void stopGroup(long jobGroupId) {
        scheduleActualHanle.stopGroup(jobGroupId);
    }

    public void continueGroup(long jobGroupId) {
        scheduleActualHanle.continueGroup(jobGroupId, retry);
    }

    public void scheduleAction(long actionId, String parameter) {
        scheduleActualHanle.scheduleAction(actionId, parameter, retry);
    }

    public void ackAction(long jobId, boolean ok) {
        scheduleActualHanle.actionAck(jobId, ok, retry);
    }


    private boolean getRetry() {
        GFlowContext context = GFlowContext.getContext();
        if (context.getConfig().getRetryActive() != null
                && context.getConfig().getRetryActive()) {
            return true;
        }
        return false;
    }
}
