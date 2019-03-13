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
 * @author gsralex
 * @version 2019/3/13
 */
public class MasterRecvExecutorHbProcess {

    private MasterReceiveExecutorHb hb;

    public MasterRecvExecutorHbProcess(final SchedulerContext context) {
        hb = new MasterReceiveExecutorHb(context);
    }

    public void start() {
        Thread t = new Thread(hb);
        t.start();
    }

    public void heartBeat(IpAddr ip, String tag) {
        hb.heartBeat(ip, tag);
    }

    public void addNode(final ExecutorNode node) {
        hb.addNode(node);
    }

    public void removeNode(final ExecutorNode node) {
        hb.removeNode(node);
    }

    public List<ExecutorNode> listExecutorNode() {
        return hb.listExecutorNode();
    }


    public static class MasterReceiveExecutorHb implements Runnable {

        private static final Logger LOG = LoggerFactory.getLogger(MasterReceiveExecutorHb.class);
        private Map<IpAddr, ExecutorNode> nodeMap = new HashMap<>();
        private boolean interrupt;
        private SchedulerContext context;

        public MasterReceiveExecutorHb(SchedulerContext context) {
            this.context = context;
            Map<String, IpManager> executorIp = context.getExecutorIpManager();
            for (Map.Entry<String, IpManager> ipManager : executorIp.entrySet()) {
                for (IpAddr ip : ipManager.getValue().listIp()) {
                    ExecutorNode node = new ExecutorNode();
                    node.setTag(ipManager.getKey());
                    node.setOnline(true);
                    nodeMap.put(ip, node);
                }
            }
        }

        public void heartBeat(IpAddr ip, String tag) {
            synchronized (nodeMap) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Heartbeat from Executor ip:{},myIp:{}", ip, context.getMyIp());
                }
                ExecutorNode node = nodeMap.get(ip);
                if (node != null) {
                    node.setTag(tag);
                    node.setLastHbTime(System.currentTimeMillis());
                    node.setOnline(true);
                    if (!node.isOnline()) {
                        addNode(node);
                    }
                    nodeMap.notify();
                } else {
                    //全新节点加入
                    node = new ExecutorNode();
                    node.setIp(ip);
                    node.setOnline(true);
                    node.setLastHbTime(System.currentTimeMillis());
                    nodeMap.put(ip, node);
                    addNode(node);
                    nodeMap.notify();
                }
            }
        }

        public void stop() {
            interrupt = true;
        }


        private void removeNode(ExecutorNode node) {
            context.getExecutorIpManager(node.getTag()).removeIp(node.getIp());
        }

        private void addNode(ExecutorNode node) {
            context.getExecutorIpManager(node.getTag()).addIp(node.getIp());
        }

        private int getOnlineNodeCnt() {
            int cnt = 0;
            for (Map.Entry<IpAddr, ExecutorNode> entry : nodeMap.entrySet()) {
                ExecutorNode node = entry.getValue();
                if (node.isOnline()) {
                    cnt++;
                }
            }
            return cnt;
        }

        public List<ExecutorNode> listExecutorNode() {
            return (List<ExecutorNode>) nodeMap.values();
        }

        @Override
        public void run() {
            while (!interrupt) {
                synchronized (nodeMap) {
                    try {
                        int cnt = getOnlineNodeCnt();
                        if (cnt == 0) {
                            nodeMap.wait();
                        }
                        for (Map.Entry<IpAddr, ExecutorNode> entry : nodeMap.entrySet()) {
                            ExecutorNode node = entry.getValue();
                            if (node.isOnline()) {
                                long timeSpan = System.currentTimeMillis() - node.getLastHbTime();
                                if (timeSpan > TimeConstants.HEARTBEAT_INTERVEL * TimeConstants.LOSE_TIMES) {
                                    node.setOnline(false);
                                    removeNode(node);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                try {
                    Thread.sleep(TimeConstants.HEARTBEAT_INTERVEL);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}