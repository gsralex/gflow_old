package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.client.RpcClientManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gsralex
 * @version 2019/3/14
 */
public class ExecutorClientManager {

    private Map<String, RpcClientManager> nodes = new ConcurrentHashMap<>();

    public RpcClientManager getRpcClientManager(String tag) {
        return nodes.get(tag);
    }

    public synchronized void setNodes(List<ExecutorNode> nodeList) {
        Map<String, List<IpAddr>> newNodes = new HashMap<>();
        for (ExecutorNode node : nodeList) {
            if (newNodes.containsKey(node.getTag())) {
                newNodes.get(node.getTag()).add(node.getIp());
            } else {
                List<IpAddr> list = new ArrayList<>();
                list.add(node.getIp());
                newNodes.put(node.getTag(), list);
            }
        }
        //add and update
        for (Map.Entry<String, List<IpAddr>> entry : newNodes.entrySet()) {
            String tag = entry.getKey();
            if (!nodes.containsKey(tag)) {
                RpcClientManager rpcClientManager = new RpcClientManager();
                rpcClientManager.updateServerNodes(entry.getValue());
                nodes.put(tag, rpcClientManager);
            } else {
                nodes.get(tag).updateServerNodes(entry.getValue());
            }
        }
        //del
        for (Map.Entry<String, RpcClientManager> entry : nodes.entrySet()) {
            if (!newNodes.containsKey(entry.getKey())) {
                entry.getValue().removeAllConnect();
                nodes.remove(entry.getKey());
            }
        }
    }

}
