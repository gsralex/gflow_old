package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.core.constants.TimeConstants;
import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.scheduler.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    private static final Logger LOG = LoggerFactory.getLogger(MSchedulerHbProcess.class);

    private SchedulerContext context;

    public MSchedulerHbProcess(SchedulerContext context) {
        this.context = context;
    }

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
        synchronized (nodeMap) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Heartbeat from Slave ip:{},myIp:{}", ip, context.getMyIp());
            }
            SchedulerNode node = nodeMap.get(ip);
            if (node != null) {
                node.setOnline(true);
                node.setLastHbTime(System.currentTimeMillis());
                nodeMap.notify();
            } else {
                //全新节点加入
                node = new SchedulerNode();
                node.setIp(ip);
                node.setMaster(false);
                node.setOnline(true);
                node.setLastHbTime(System.currentTimeMillis());
                nodeMap.put(ip, node);
                nodeMap.notify();
            }
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
                synchronized (nodeMap) {
                    int cnt = 0;
                    for (Map.Entry<IpAddr, SchedulerNode> entry : nodeMap.entrySet()) {
                        SchedulerNode node = entry.getValue();
                        if (node.isOnline()) {
                            cnt++;
                        }
                    }
                    if (cnt == 0) {
                        nodeMap.wait();
                    }
                }
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
                LOG.error("MSchedulerHbProcess.mainLoop", e);
            }
        }
    }

    public List<IpAddr> listOnlineSlaveIp() {
        List<IpAddr> list = new ArrayList<>();
        for (Map.Entry<IpAddr, SchedulerNode> entry : nodeMap.entrySet()) {
            if (entry.getValue().isOnline() && !entry.getValue().isMaster()) {
                list.add(entry.getValue().getIp());
            }
        }
        return list;
    }

    public static void main(String[] args) {
        SchedulerContext context = new SchedulerContext();
        MSchedulerHbProcess process = new MSchedulerHbProcess(context);
        process.start();
        process.heartBeat(new IpAddr("123", 123));
        process.heartBeat(new IpAddr("124", 123));
        process.heartBeat(new IpAddr("123", 123));
    }
}
