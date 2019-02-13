package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.scheduler.client.impl.ScheduleClientImpl;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/12
 */
public class ScheduleClientFactory {

    public static ScheduleClient create(IpAddress ip, String accessToken) {
        return new ScheduleClientImpl(ip, accessToken);
    }

    public static ScheduleClient create(List<IpAddress> ipList, String accessToken) {
        return new ScheduleClientImpl(ipList, accessToken);
    }
}
