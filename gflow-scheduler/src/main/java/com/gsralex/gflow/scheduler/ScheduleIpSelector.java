package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.core.context.IpAddress;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public interface ScheduleIpSelector {

    IpAddress getIpAddress(long tagId);
}
