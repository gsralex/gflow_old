package com.gsralex.gflow.core.rpc.server;

import com.gsralex.gflow.core.rpc.protocol.RpcReq;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcServerHandlerTest {
    @Test
    public void doAction() throws Exception {

        ServiceCache serviceCache = new ServiceCache();
        RpcServerHandler handler = new RpcServerHandler(serviceCache);
        handler.registerHandler(TestService.class, new TestServiceImpl());

        RpcReq req0 = new RpcReq();
        req0.setClassName("com.gsralex.gflow.core.rpc.server.TestService");
        req0.setMethodName("foo0");
        req0.setParameters(new Object[]{1});
        Assert.assertEquals(0, handler.doAction(req0));

        RpcReq req01 = new RpcReq();
        req01.setClassName("com.gsralex.gflow.core.rpc.server.TestService");
        req01.setMethodName("foo0");
        req01.setParameters(new Object[]{1});
        Assert.assertEquals(0, handler.doAction(req01));

        RpcReq req1 = new RpcReq();
        req1.setClassName("com.gsralex.gflow.core.rpc.server.TestService");
        req1.setMethodName("foo1");
        req1.setParameters(new Object[]{1, "d"});
        Assert.assertEquals(1, handler.doAction(req1));

    }

}