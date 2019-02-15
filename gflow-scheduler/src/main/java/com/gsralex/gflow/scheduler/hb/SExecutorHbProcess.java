package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.context.IpAddr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 只接受master的更新executor状态
 *
 * @author gsralex
 * @version 2019/2/14
 */
public class SExecutorHbProcess implements ExecutorHbProcess {

    private Map<IpAddr, ExecutorNode> nodeMap = new HashMap<>();

    public void setIps(List<IpAddr> list) {
        for (IpAddr ip : list) {
            ExecutorNode executorNode = new ExecutorNode();
            executorNode.setIp(ip);
            executorNode.setOnline(true);
            nodeMap.put(ip, executorNode);
        }
    }

    public void update(IpAddr ip, String tag, boolean online) {
        ExecutorNode node = nodeMap.get(ip);
        if (node != null) {
            node.setTag(tag);
            node.setOnline(online);
        } else {
            //全新节点加入
            node = new ExecutorNode();
            node.setIp(ip);
            node.setTag(tag);
            node.setOnline(online);
            nodeMap.put(ip, node);
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
