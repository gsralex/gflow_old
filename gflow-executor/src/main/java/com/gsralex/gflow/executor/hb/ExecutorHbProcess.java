package com.gsralex.gflow.executor.hb;

import com.gsralex.gflow.pub.constants.TimeConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.util.IpSelector;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.ExecutorHbReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2019/2/15
 */
public class ExecutorHbProcess {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutorHbProcess.class);

    private ExecutorContext context;
    private boolean interrupt;

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

    public void stop() {
        interrupt = true;
    }

    private void mainLoop() {
        IpSelector ipSelector = new IpSelector(context.getScheduleIps());
        while (!interrupt) {
            try {
                IpAddr schedulerIp = ipSelector.getIp();
                SchedulerClient client = SchedulerClientFactory.create(schedulerIp, context.getAccessToken());
                ExecutorHbReq req = new ExecutorHbReq();
                IpAddr myIp = context.getMyIp();
                req.setIp(myIp.getIp());
                req.setPort(myIp.getPort());
                req.setTag(context.getConfig().getTag());
                client.executorHb(req);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Executor heartbeat ip:{},myIp:{}", schedulerIp.toString(), myIp.toString());
                }
                Thread.sleep(TimeConstants.HEARTBEAT_INTERVEL);
            } catch (Exception e) {
                LOG.error("SSchedulerHbProcess.mainLoop", e);
            }
        }
    }
}
