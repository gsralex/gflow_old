package com.gsralex.gflow.scheduler.service;

import com.gsralex.gflow.scheduler.schedule.ActionResult;
import com.gsralex.gflow.scheduler.schedule.FlowResult;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface SchedulerService {

    FlowResult scheduleGroup(long triggerGroupId,
                             String parameter,
                             long timerConfigId,
                             boolean retry);

    void pauseGroup(long jobGroupId);

    void stopGroup(long jobGroupId);

    FlowResult continueGroup(long jobGroupId, boolean retry);

    ActionResult scheduleAction(long actionId, String parameter, boolean retry);

    boolean ackAction(long jobId, int code, String msg, boolean retry);

    boolean ackMaster(long jobId, int code, String msg, boolean retry);
}
