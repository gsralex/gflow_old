package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.server.TExecutorServer;
import org.apache.thrift.transport.TTransportException;
import org.springframework.context.ApplicationContext;

import java.io.IOException;


/**
 * @author gsralex
 * @version 2018/8/4
 */
public class ExecutorServer {

    private ExecutorContext context;
    public ExecutorServer() throws IOException {
        context = new ExecutorContext();
        context.init();
    }

    public void setSpringContext(ApplicationContext context) {
        this.context.setSpringContext(context);
    }

    public void serve() throws TTransportException {
        TExecutorServer server = new TExecutorServer(context);
        server.start();
    }

    public static void main(String[] args) {
        ExecutorServer server;
    }

}
