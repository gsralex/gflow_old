package com.gsralex.gflow.core.rpc;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.client.RpcClientFactory;
import com.gsralex.gflow.core.rpc.client.RpcClientManager;
import com.gsralex.gflow.core.rpc.server.TestService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class RpcClientTest {

    @Test
    public void client() throws Throwable {
        List<IpAddr> ipList = new ArrayList<>();
        ipList.add(new IpAddr("localhost", 20091));
        RpcClientManager.getInstance().updateServerNodes(ipList);
        TestService testService = RpcClientFactory.create(TestService.class);
        Assert.assertEquals(testService.foo0(1), 0);
        Assert.assertEquals(testService.foo0(1, "12312312"), 0);
        Assert.assertEquals(testService.foo1(1, "123123"), 1);
    }
}
