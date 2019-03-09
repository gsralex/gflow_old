package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.client.impl.SchedulerClientImpl;
import com.gsralex.gflow.scheduler.client.impl.TimerClientImpl;

/**
 * @author gsralex
 * @version 2019/2/12
 */
public class SchedulerClientFactory {

    public static SchedulerClient createScheduler(IpAddr ip, String accessToken) {
        return new SchedulerClientImpl(ip, accessToken);
    }

    public static TimerClient createTimer(IpAddr ip, String accessToken) {
        return new TimerClientImpl(ip);
    }
}
