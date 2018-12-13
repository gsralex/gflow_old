package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.executor.server.TExecutorServer;
import org.springframework.context.ApplicationContext;


/**
 * @author gsralex
 * @version 2018/8/4
 */
public class ExecutorServer {

    public void setSpringContext(ApplicationContext context) {
        ExecutorContext.getContext().setSpringContext(context);
    }

    public void start() {
        GFlowContext context = GFlowContext.getContext();
        context.initConfig();
        ExecutorContext executorContext = ExecutorContext.getContext();
        executorContext.setGflowContext(context);
        TExecutorServer server = new TExecutorServer(executorContext);
        server.start(context.getConfig().getPort());
    }

    public static void main(String[] args){
        ExecutorServer server;
    }

}
