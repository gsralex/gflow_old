package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.hb.ExecutorHbProcess;
import com.gsralex.gflow.executor.server.TExecutorServer;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;


/**
 * @author gsralex
 * @version 2018/8/4
 */
public class ExecutorServer {


    private static final Logger LOG = LoggerFactory.getLogger(ExecutorServer.class);

    private ExecutorContext context;

    public ExecutorServer() throws IOException {
        context = ExecutorContext.getInstance();
        context.init();
    }

    public void setSpringContext(ApplicationContext context) {
        this.context.setSpringContext(context);
    }

    public void serve() throws TTransportException {
        LOG.info("====== ExecutorServer STARTING ======");
        LOG.info("port:{}", context.getConfig().getPort());
        TExecutorServer server = new TExecutorServer(context);
        server.start();
        LOG.info("====== ExecutorServer STARTED ======");

        ExecutorHbProcess process = new ExecutorHbProcess(context);
        process.start();
    }

    public static void main(String[] args) throws IOException, TTransportException {
        ExecutorServer server = new ExecutorServer();
        server.serve();
    }

}
