package com.gsralex.gflow.core.rpc;

import com.gsralex.gflow.core.rpc.server.RpcServer;
import com.gsralex.gflow.core.rpc.server.TestService;
import com.gsralex.gflow.core.rpc.server.TestServiceImpl;
import org.junit.Test;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class RpcServerTest {

    @Test
    public void serve() throws Exception {
        RpcServer rpcServer = new RpcServer();
        rpcServer.registerHandler(TestService.class, new TestServiceImpl());
        rpcServer.serve(20091);
    }
}
