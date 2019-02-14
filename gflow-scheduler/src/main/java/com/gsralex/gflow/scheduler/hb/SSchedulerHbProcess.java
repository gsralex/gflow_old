package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.core.constants.TimeConstants;
import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.ScheduleHbReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slave scheduler 心跳处理
 * 向master上报心跳
 *
 * @author gsralex
 * @version 2019/2/14
 */
public class SSchedulerHbProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSchedulerHbProcess.class);
    private SchedulerContext context;
    private boolean interrupt;

    public SSchedulerHbProcess(SchedulerContext context) {
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
        interrupt = false;
    }

    private void mainLoop() {
        while (!interrupt) {
            try {
                IpAddr ip = context.getMasterIp();
                SchedulerClient client = SchedulerClientFactory.create(ip, context.getAccessToken());
                ScheduleHbReq req = new ScheduleHbReq();
                req.setIp(context.getMyIp().getIp());
                req.setPort(context.getMyIp().getPort());
                client.schedulerHb(req);
                Thread.sleep(TimeConstants.HEARTBEAT_INTERVEL);
            } catch (Exception e) {
                LOGGER.error("SSchedulerHbProcess.mainLoop", e);
            }
        }
    }
}
