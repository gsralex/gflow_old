package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.executor.thrift.TExecutorServer;

import java.io.IOException;


/**
 * @author gsralex
 * @version 2018/8/4
 */
public class ExecutorServer {

    public static void main(String[] args) throws IOException {
        ExecutorServer server = new ExecutorServer();
        server.start();
    }


    public void start() {

        GFlowContext context = new GFlowContext();
        context.init();

        TExecutorServer server = new TExecutorServer(context);
        server.start(context.getConfig().getPort());
    }
}