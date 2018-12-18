package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.thriftgen.TResp;

/**
 * @author gsralex
 * @version 2018/12/13
 */
public interface ScheduleCallback {

    TResp doSchedule();
}
