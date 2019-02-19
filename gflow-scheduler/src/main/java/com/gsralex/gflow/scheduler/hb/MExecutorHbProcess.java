package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.constants.TimeConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.ExecutorHbReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/2/6
 */
public class MExecutorHbProcess implements ExecutorHbProcess {

    private static Logger LOG = LoggerFactory.getLogger(MExecutorHbProcess.class);
    private Map<IpAddr, ExecutorNode> nodeMap = new HashMap<>();
    private boolean interrupt;
    private SchedulerContext context;
    private ExecutorIpSelector ipSelector;

    public MExecutorHbProcess(SchedulerContext context) {
        this.context = context;
        ipSelector = new ExecutorIpSelector();
    }

    public void setIps(List<IpAddr> list) {
        for (IpAddr ip : list) {
            ExecutorNode executorNode = new ExecutorNode();
            executorNode.setIp(ip);
            executorNode.setOnline(false);
            nodeMap.put(ip, executorNode);
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
                if (!node.isOnline()) {
                    notifySlaveNode(node);
                }
                node.setOnline(true);
                nodeMap.notify();
            } else {
                //全新节点加入
                node = new ExecutorNode();
                node.setIp(ip);
                node.setOnline(true);
                node.setLastHbTime(System.currentTimeMillis());
                nodeMap.put(ip, node);
                notifySlaveNode(node);
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
                    for (Map.Entry<IpAddr, ExecutorNode> entry : nodeMap.entrySet()) {
                        ExecutorNode node = entry.getValue();
                        if (node.isOnline()) {
                            cnt++;
                        }
                    }
                    if (cnt == 0) {
                        nodeMap.wait();
                    }
                }
                for (Map.Entry<IpAddr, ExecutorNode> entry : nodeMap.entrySet()) {
                    ExecutorNode node = entry.getValue();
                    if (node.isOnline()) {
                        long timeSpan = System.currentTimeMillis() - node.getLastHbTime();
                        if (timeSpan > TimeConstants.HEARTBEAT_INTERVEL * TimeConstants.LOSE_TIMES) {
                            node.setOnline(false);
                            notifySlaveNode(node);
                        }
                    }
                }
                Thread.sleep(TimeConstants.HEARTBEAT_INTERVEL);
            } catch (Exception e) {
            }
        }
    }


    /**
     * master scheduler通知
     */
    private void notifySlaveNode(ExecutorNode node) {
        List<IpAddr> ipList = context.getHbContext().getmSchedulerHbProcess().listOnlineSlaveIp();
        //轮播
        for (IpAddr nodeIp : ipList) {
            try {
                SchedulerClient client = new SchedulerClientFactory().create(nodeIp, context.getAccessToken());
                ExecutorHbReq req = new ExecutorHbReq();
                req.setIp(node.getIp().getIp());
                req.setPort(node.getIp().getPort());
                req.setOnline(node.isOnline());
                req.setTag(node.getTag());
                client.updateExecutorNode(req);
            } catch (Exception e) {
                LOG.error("MExecutorHbProcess.notifySlaveNode ip:{}", nodeIp, e);
            }
        }
    }

    /**
     * 根据标签获取当前在线的executor
     *
     * @param tag
     * @return
     */
    @Override
    public List<IpAddr> listOnlineIp(String tag) {
        List<IpAddr> onelineList = new ArrayList<>();
        for (Map.Entry<IpAddr, ExecutorNode> entry : nodeMap.entrySet()) {
            if (tag.equals(entry.getValue().getTag())) {
                if (entry.getValue().isOnline()) {
                    onelineList.add(entry.getKey());
                }
            }
        }
        return onelineList;
    }

    @Override
    public IpAddr getOnlineIpSeq(String tag) {
        return ipSelector.getTagIpSeq(tag, listOnlineIp(tag));
    }
}
