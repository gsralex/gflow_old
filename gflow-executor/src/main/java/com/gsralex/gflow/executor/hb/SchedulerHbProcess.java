package com.gsralex.gflow.executor.hb;

import com.gsralex.gflow.pub.context.IpAddr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/2/18
 */
public class SchedulerHbProcess {

    private Map<IpAddr, SchedulerNode> nodeMap = new HashMap<>();

    public synchronized void update(IpAddr ip, boolean online) {
        if (nodeMap.containsKey(ip)) {
            SchedulerNode node = nodeMap.get(ip);
            if (node != null) {
                node.setOnline(online);
            }
        } else {
            SchedulerNode node = new SchedulerNode();
            node.setIp(ip);
            node.setOnline(online);
            nodeMap.put(ip, node);
        }
    }

    public List<IpAddr> listOnlineIp() {
        List<IpAddr> ipList = new ArrayList<>();
        for (Map.Entry<IpAddr, SchedulerNode> entry : nodeMap.entrySet()) {
            if (entry.getValue().isOnline()) {
                ipList.add(entry.getValue().getIp());
            }
        }
        return ipList;
    }

}
