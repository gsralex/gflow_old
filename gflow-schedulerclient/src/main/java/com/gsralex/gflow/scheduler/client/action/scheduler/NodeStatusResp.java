package com.gsralex.gflow.scheduler.client.action.scheduler;

import com.gsralex.gflow.pub.action.Resp;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/13
 */
public class NodeStatusResp extends Resp {

    private List<NodeStatus> nodeList;

    public List<NodeStatus> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeStatus> nodeList) {
        this.nodeList = nodeList;
    }
}
