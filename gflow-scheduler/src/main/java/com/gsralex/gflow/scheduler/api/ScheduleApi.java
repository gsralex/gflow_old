package com.gsralex.gflow.scheduler.api;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class ScheduleApi {

    private GFlowContext context;
    private ScheduleLinkHandle scheduleLinkHandle;

    public ScheduleApi(GFlowContext context) {
        this.context = context;
    }

    public void scheduleGroup(long groupId, Parameter parameter) {
        scheduleLinkHandle.scheduleGroup(groupId, parameter, 0);
    }

    public void scheduleAction(long actionId, Parameter parameter) {
        scheduleLinkHandle.scheduleAction(actionId, parameter);
    }

    public static void main(String[] args) {
        GFlowContext context = GFlowContext.getContext();
        ScheduleApi api = new ScheduleApi(context);
        Parameter parameter = new Parameter();
        api.scheduleGroup(1, parameter);
    }
}
