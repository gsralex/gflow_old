package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.client.RpcClientFactory;
import com.gsralex.gflow.core.rpc.client.RpcClientManager;
import com.gsralex.gflow.scheduler.client.domain.FlowGroup;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/22
 */
public class FlowServiceTest {

    @Test
    public void setFlowGroup() {
        List<IpAddr> ipList = new ArrayList<>();
        ipList.add(new IpAddr("localhost", 20091));
        RpcClientManager clientManager = new RpcClientManager(ipList);
        FlowService flowService = RpcClientFactory.create(FlowService.class, clientManager);

        FlowGroup flowGroup = new FlowGroup();
        flowGroup.setName("测试流程");
        flowGroup.setDescription("测试流程");
        flowGroup.action(1).label("测试流程1").index(1);
        flowGroup.action(2).label("测试流程2").index(2);
        flowGroup.direct(1).next(2);
        flowService.setFlowGroup(flowGroup);
        long st = System.currentTimeMillis();
        flowService.setFlowGroup(flowGroup);
        long et = System.currentTimeMillis();
        long ts = et - st;
        System.out.println("ts:" + ts);
        long st1 = System.currentTimeMillis();
        flowService.setFlowGroup(flowGroup);
        long et1 = System.currentTimeMillis();
        long ts1 = et1 - st1;
        System.out.println("ts1:" + ts1);

    }

}