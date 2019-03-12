package com.gsralex.gflow.executor.hb;

import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.pub.constants.TimeConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.ExecutorHbReq;
import com.gsralex.gflow.scheduler.client.action.scheduler.SchedulerNodeResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2019/2/15
 */
public class ExecutorHbProcess {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutorHbProcess.class);
    private ExecutorContext context;

    public ExecutorHbProcess(ExecutorContext context) {
        this.context = context;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLoop();
            }
        }).start();
    }

    private void mainLoop() {
        while (true) {
            try {
                IpAddr ip = context.getSchedulerIpManager().getIp();
                SchedulerClient client = SchedulerClientFactory.createScheduler(ip, context.getConfig().getAccessKey());
                ExecutorHbReq req = new ExecutorHbReq();
                IpAddr myIp = context.getMyIp();
                req.setIp(myIp.getIp());
                req.setPort(myIp.getPort());
                req.setTag(context.getConfig().getTag());
                SchedulerNodeResp resp = client.executorHb(req);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Executor heartbeat ip:{},myIp:{}", ip, myIp.toString());
                }
                context.getSchedulerIpManager().setIp(resp.getNodeList());
                Thread.sleep(TimeConstants.HEARTBEAT_INTERVEL);
            } catch (Exception e) {
                LOG.error("SSchedulerHbProcess.mainLoop", e);
            }
        }
    }
}
