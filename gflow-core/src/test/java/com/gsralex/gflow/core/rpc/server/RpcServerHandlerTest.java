package com.gsralex.gflow.core.rpc.server;

import com.gsralex.gflow.core.rpc.protocol.RpcReq;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcServerHandlerTest {
    @Test
    public void doAction() throws Exception {

        Map<String, Object> serverHandlers = new HashMap<>();
        RpcServerHandler handler = new RpcServerHandler(serverHandlers);
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