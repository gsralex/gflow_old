package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.hb.ExecutorHbProcess;
import com.gsralex.gflow.executor.hb.HbService;
import com.gsralex.gflow.executor.server.TExecutorServer;
import com.gsralex.gflow.pub.context.IpAddr;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

        initSchedulerIp();
        ExecutorHbProcess process = new ExecutorHbProcess(context);
        process.start();
    }

    private void initSchedulerIp() {
        String schedulerIps = context.getConfig().getSchedulerIps();
        String[] ips = schedulerIps.split(",");
        List<IpAddr> ipList = new ArrayList<>();
        for (String ip : ips) {
            ipList.add(new IpAddr(ip));
        }
        HbService hbService = new HbService(context);
        hbService.listSchedulerNode(ipList.get(0));
    }

    public static void main(String[] args) throws IOException, TTransportException {
        ExecutorServer server = new ExecutorServer();
        server.serve();
    }

}
