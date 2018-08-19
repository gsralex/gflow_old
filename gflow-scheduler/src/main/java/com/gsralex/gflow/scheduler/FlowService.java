package com.gsralex.gflow.scheduler;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface FlowService {

    void startGroup(long triggerGroupId, String parameter,long executeConfigId);

    void pauseGroup(long jobGroupId);

    void startAction(long actionId, String parameter);

    void actionAck(long scheduleId, boolean jobOk);
}
