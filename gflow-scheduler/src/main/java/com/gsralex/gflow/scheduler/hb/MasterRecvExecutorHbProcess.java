package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.constants.TimeConstants;
import com.gsralex.gflow.pub.context.IpAddr;
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

    public void stop() {
        hb.stop();
    }

    public void heartBeat(IpAddr ip, String tag) {
        hb.heartBeat(ip, tag);
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
            for (ExecutorNode node : context.getExecutorIpManager().listNode()) {
                nodeMap.put(node.getIp(), node);
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
                    addContextNode(node);
                    nodeMap.notify();
                } else {
                    //全新节点加入
                    node = new ExecutorNode();
                    node.setIp(ip);
                    node.setOnline(true);
                    node.setLastHbTime(System.currentTimeMillis());
                    nodeMap.put(ip, node);
                    addContextNode(node);
                    nodeMap.notify();
                }
            }
        }

        public void stop() {
            interrupt = true;
        }


        private void removeContextNode(ExecutorNode node) {
            context.getExecutorIpManager().removeNode(node.getIp(), node.getTag());
        }

        private void addContextNode(ExecutorNode node) {
            context.getExecutorIpManager().addNode(node.getIp(), node.getTag());
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
                                    removeContextNode(node);
                                    LOG.info("Loss ExecutorNode ip:{},tag:{},lastHeatbeatsTime:{}",
                                            node.getIp(), node.getTag(), node.getLastHbTime());
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("MasterRecvExecutorProcess", e);
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
