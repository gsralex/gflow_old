package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.server.RpcServer;
import com.gsralex.gflow.executor.client.ExecutorService;
import com.gsralex.gflow.executor.registry.ZkExecutorRegistry;
import com.gsralex.gflow.executor.server.ExecutorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;


/**
 * @author gsralex
 * @version 2018/8/4
 */
public class ExecutorServer {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutorServer.class);

    private ExecutorContext context;

    public void setSpringContext(ApplicationContext context) {
        this.context.setSpringContext(context);
    }

    public void serve() throws Exception {
        context = ExecutorContext.getInstance();
        context.init();
        handleNodes();
        LOG.info("====== ExecutorServer STARTING ======");
        LOG.info("port:{}", context.getConfig().getPort());
        RpcServer server = new RpcServer();
        server.registerHandler(ExecutorService.class, new ExecutorServiceImpl());
        server.serve(context.getConfig().getPort());
        LOG.info("====== ExecutorServer STARTED ======");
    }

    private void handleNodes() {
        if (context.getConfig().isZkActive()) {
            ZkExecutorRegistry registry = new ZkExecutorRegistry();
            context.getSchedulerIpManager().updateServerNodes(registry.listScheduler());
            registry.register();
            registry.subscribeScheduler();
        } else {
            String ips = context.getConfig().getSchedulerIps();
            List<IpAddr> ipList = IpAddr.getIpsByConfig(ips);
            context.getSchedulerIpManager().updateServerNodes(ipList);
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorServer server = new ExecutorServer();
        server.serve();
    }

}
