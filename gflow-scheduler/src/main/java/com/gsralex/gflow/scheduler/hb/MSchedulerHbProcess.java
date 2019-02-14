package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.core.constants.TimeConstants;
import com.gsralex.gflow.core.context.IpAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Master Scheduler 心跳处理
 *
 * @author gsralex
 * @version 2019/2/14
 */
public class MSchedulerHbProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(MSchedulerHbProcess.class);

    private Map<IpAddr, SchedulerNode> nodeMap = new HashMap<>();
    private boolean interrupt;

    public void setIps(List<IpAddr> list) {
        for (IpAddr ip : list) {
            SchedulerNode node = new SchedulerNode();
            node.setIp(ip);
            node.setOnline(false);
            nodeMap.put(ip, node);
        }
    }

    public void heartBeat(IpAddr ip) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("heartbeart:{}", ip);
        }
        SchedulerNode node = nodeMap.get(ip);
        if (node != null) {
            node.setOnline(true);
            node.setLastHbTime(System.currentTimeMillis());
        } else {
            //全新节点加入
            node = new SchedulerNode();
            node.setIp(ip);
            node.setMaster(false);
            node.setOnline(true);
            node.setLastHbTime(System.currentTimeMillis());
            nodeMap.put(ip, node);
        }
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

    public void mainLoop() {
        while (!interrupt) {
            try {
                for (Map.Entry<IpAddr, SchedulerNode> entry : nodeMap.entrySet()) {
                    SchedulerNode node = entry.getValue();
                    if (node.isOnline()) {
                        long timeSpan = System.currentTimeMillis() - node.getLastHbTime();
                        if (timeSpan > TimeConstants.HEARTBEAT_INTERVEL * TimeConstants.LOSE_TIMES) {
                            node.setOnline(false);
                        }
                    }
                }
                Thread.sleep(TimeConstants.HEARTBEAT_INTERVEL);
            } catch (Exception e) {
                LOGGER.error("MSchedulerHbProcess.mainLoop", e);
            }
        }
    }
}
