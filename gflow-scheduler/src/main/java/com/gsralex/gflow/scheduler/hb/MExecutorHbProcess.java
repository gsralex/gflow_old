package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.core.constants.TimeConstants;
import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.scheduler.client.SchedulerClient;
import com.gsralex.scheduler.client.SchedulerClientFactory;
import com.gsralex.scheduler.client.action.scheduler.ExecutorHbReq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/2/6
 */
public class MExecutorHbProcess implements ExecutorHbProcess {

    private Map<IpAddr, ExecutorNode> nodeMap = new HashMap<>();
    private boolean interrupt;
    private SchedulerContext context;

    public MExecutorHbProcess(SchedulerContext context) {
        this.context = context;
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
        ExecutorNode node = nodeMap.get(ip);
        if (node != null) {
            node.setTag(tag);
            node.setLastHbTime(System.currentTimeMillis());
            if (!node.isOnline()) {
                masterNotifyNode(node);
            }
            node.setOnline(true);
        } else {
            //全新节点加入
            node = new ExecutorNode();
            node.setIp(ip);
            node.setOnline(true);
            node.setLastHbTime(System.currentTimeMillis());
            nodeMap.put(ip, node);
            masterNotifyNode(node);
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
            for (Map.Entry<IpAddr, ExecutorNode> entry : nodeMap.entrySet()) {
                ExecutorNode node = entry.getValue();
                if (node.isOnline()) {
                    long timeSpan = System.currentTimeMillis() - node.getLastHbTime();
                    if (timeSpan > TimeConstants.HEARTBEAT_INTERVEL * TimeConstants.LOSE_TIMES) {
                        node.setOnline(false);
                        masterNotifyNode(node);
                    }
                }
            }
            try {
                Thread.sleep(TimeConstants.HEARTBEAT_INTERVEL);
            } catch (InterruptedException e) {
            }
        }
    }


    /**
     * master scheduler通知
     */
    private void masterNotifyNode(ExecutorNode node) {
        List<IpAddr> ipList = context.getSchedulerIps();
        //轮播
        for (IpAddr nodeIp : ipList) {
            SchedulerClient client = new SchedulerClientFactory().create(nodeIp, context.getAccessToken());
            ExecutorHbReq req = new ExecutorHbReq();
            req.setIp(node.getIp().getIp());
            req.setPort(node.getIp().getPort());
            req.setOnline(node.isOnline());
            req.setTag(node.getTag());
            client.updateExecutorNode(req);
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
}
