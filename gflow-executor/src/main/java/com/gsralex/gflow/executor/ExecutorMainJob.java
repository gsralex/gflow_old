package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.executor.thrift.TExecutorServer;


/**
 * @author gsralex
 * @version 2018/8/4
 */
public class ExecutorMainJob {

    public static void main(String[] args) {
        ExecutorMainJob mainJob = new ExecutorMainJob();
        mainJob.start();
    }


    public void start() {

        GFlowContext context = new GFlowContext();
        context.init();

        TExecutorServer server = new TExecutorServer(context);
        server.start(context.getConfig().getPort());

    }
}
