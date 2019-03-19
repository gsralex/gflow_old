package com.gsralex.gflow.scheduler.service;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface BizTimerService {

    boolean saveTimer(long flowGroupId, boolean active, String time);

    boolean updateTimer(long id, long flowGroupId, boolean active, String time);

    boolean removeTimer(long id);
}
