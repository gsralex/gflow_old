package com.gsralex.gflow.scheduler.client;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public interface ScheduleService {
    boolean scheduleAction(long actionId, String parameter);

    boolean scheduleGroup(long groupId, String parameter, long timerConfigId);

    boolean ack(long jobId, boolean ok, String msg);
}
