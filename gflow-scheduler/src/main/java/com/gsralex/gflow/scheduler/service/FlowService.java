package com.gsralex.gflow.scheduler.service;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface FlowService {

    void startGroup(long triggerGroupId, String parameter);

    void pauseGroup(long jobGroupId);

    void startAction(long actionId, String parameter);
}
