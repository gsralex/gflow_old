package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.client.RpcClientFactory;
import com.gsralex.gflow.core.rpc.client.RpcClientManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/22
 */
public class ScheduleServiceTest {
    @Test
    public void scheduleAction() throws Exception {
        List<IpAddr> ipList = new ArrayList<>();
        ipList.add(new IpAddr("localhost", 20091));
        RpcClientManager clientManager = new RpcClientManager(ipList);
        ScheduleService scheduleService = RpcClientFactory.create(ScheduleService.class, clientManager);
        scheduleService.scheduleGroup(1, "bizdate=20190326", 0);
    }


}