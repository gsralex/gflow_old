package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.executor.demo.SpringConfiguration;
import com.gsralex.gflow.executor.thrift.TExecutorServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


/**
 * @author gsralex
 * @version 2018/8/4
 */
public class ExecutorServer {

    public static void main(String[] args) throws IOException {
        ExecutorServer server = new ExecutorServer();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        ExecutorContext.getContext().setSpringContext(context);
        server.start();
    }


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

}
