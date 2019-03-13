package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.constants.TimeConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.context.IpManager;
import com.gsralex.gflow.scheduler.SchedulerContext;
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
public class MasterReceiveSchedulerHb {

    private static final Logger LOG = LoggerFactory.getLogger(MasterReceiveSchedulerHb.class);


    private static Map<IpAddr, SchedulerNode> nodeMap = new HashMap<>();
    private boolean interrupt;

    private SchedulerContext context;

    public MasterReceiveSchedulerHb(SchedulerContext context) {
        this.context = context;
        IpManager ipManager = context.getSchedulerIpManager();
        for (IpAddr ip : ipManager.listIp()) {
            SchedulerNode node = new SchedulerNode();
            node.setIp(ip);
            node.setOnline(true);
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
                if (!node.isOnline()) {
                    addNode(node);
                }
                nodeMap.notify();
            } else {
                //全新节点加入
                node = new SchedulerNode();
                node.setIp(ip);
                node.setMaster(false);
                node.setOnline(true);
                node.setLastHbTime(System.currentTimeMillis());
                nodeMap.put(ip, node);
                addNode(node);
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
                int cnt = getOnlineNodeCnt();
                if (cnt == 0) {
                    synchronized (nodeMap) {
                        nodeMap.wait();
                    }
                }
                for (Map.Entry<IpAddr, SchedulerNode> entry : nodeMap.entrySet()) {
                    SchedulerNode node = entry.getValue();
                    if (node.isOnline()) {
                        long timeSpan = System.currentTimeMillis() - node.getLastHbTime();
                        if (timeSpan > TimeConstants.HEARTBEAT_INTERVEL * TimeConstants.LOSE_TIMES) {
                            node.setOnline(false);
                            removeNode(node);
                        }
                    }
                }
                Thread.sleep(TimeConstants.HEARTBEAT_INTERVEL);
            } catch (Exception e) {
                LOG.error("MasterReceiveSchedulerHb.mainLoop", e);
            }
        }
    }


    private void removeNode(SchedulerNode node) {
        context.getSchedulerIpManager().removeIp(node.getIp());
    }

    private void addNode(SchedulerNode node) {
        context.getSchedulerIpManager().addIp(node.getIp());
    }

    private int getOnlineNodeCnt() {
        int cnt = 0;
        for (Map.Entry<IpAddr, SchedulerNode> entry : nodeMap.entrySet()) {
            SchedulerNode node = entry.getValue();
            if (node.isOnline()) {
                cnt++;
            }
        }
        return cnt;
    }

    public List<SchedulerNode> listNode() {
        return (List<SchedulerNode>) nodeMap.values();
    }

    public static void main(String[] args) {

        MasterReceiveSchedulerHb process = new MasterReceiveSchedulerHb(SchedulerContext.getInstance());
        process.start();
        process.heartBeat(new IpAddr("123", 123));
        process.heartBeat(new IpAddr("124", 123));
        process.heartBeat(new IpAddr("123", 123));
    }
}
