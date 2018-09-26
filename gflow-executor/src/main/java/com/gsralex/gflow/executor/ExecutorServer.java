package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.GFlowContext;
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
        ApplicationContext context = new AnnotationConfigApplicationContext();
        ExecutorContext.getContext().setSpringApplicationContext(context);
        server.start();
    }


    public void start() {

        GFlowContext context = GFlowContext.getContext();
        context.init();

        TExecutorServer server = new TExecutorServer(context);
        server.start(context.getConfig().getPort());
    }

}
