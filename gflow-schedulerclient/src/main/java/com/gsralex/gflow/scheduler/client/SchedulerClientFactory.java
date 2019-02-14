package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.scheduler.client.impl.SchedulerClientImpl;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/12
 */
public class SchedulerClientFactory {

    public static SchedulerClient create(IpAddr ip, String accessToken) {
        return new SchedulerClientImpl(ip, accessToken);
    }

    public static SchedulerClient create(List<IpAddr> ipList, String accessToken) {
        return new SchedulerClientImpl(ipList, accessToken);
    }
}
