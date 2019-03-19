package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.scheduler.client.domain.TimerReq;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public interface TimerService {
    boolean addTimer(TimerReq req);
    boolean updateTimer(TimerReq req);
    boolean removeTimer(long id);
}
