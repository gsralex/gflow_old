package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.context.IpManager;
import com.gsralex.gflow.scheduler.hb.ExecutorNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gsralex
 * @version 2019/3/14
 */
public class ExecutorIpManager {

    private Map<String, IpManager> ipMap = new ConcurrentHashMap<>();

    public List<ExecutorNode> listNode() {
        List<ExecutorNode> nodeList = new ArrayList<>();
        for (Map.Entry<String, IpManager> entry : ipMap.entrySet()) {
            for (IpAddr ip : entry.getValue().listIp()) {
                ExecutorNode node = new ExecutorNode();
                node.setIp(ip);
                node.setTag(entry.getKey());
                nodeList.add(node);
            }
        }
        return nodeList;
    }

    public synchronized void addNode(final IpAddr ip, final String tag) {
        if (ipMap.containsKey(tag)) {
            IpManager ipManager = ipMap.get(tag);
            ipManager.addIp(ip);
        } else {
            ipMap.put(tag, new IpManager(ip));
        }
    }

    public synchronized void removeNode(final IpAddr ip, final String tag) {
        if (ipMap.get(tag) != null) {
            ipMap.get(tag).removeIp(ip);
        }
    }

    public synchronized void addNodeList(final List<IpAddr> ipList, final String tag) {
        if (ipMap.containsKey(tag)) {
            for (IpAddr ip : ipList) {
                ipMap.get(tag).addIp(ip);
            }
        } else {
            IpManager ipManager = new IpManager(ipList);
            ipMap.put(tag, ipManager);
        }
    }

    public IpAddr getIp(String tag) {
        if (ipMap.containsKey(tag)) {
            return ipMap.get(tag).getIp();
        }
        return null;
    }
}
