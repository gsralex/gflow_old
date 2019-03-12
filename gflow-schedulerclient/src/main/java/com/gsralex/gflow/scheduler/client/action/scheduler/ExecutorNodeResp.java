package com.gsralex.gflow.scheduler.client.action.scheduler;

import com.gsralex.gflow.pub.action.Resp;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/12
 */
public class ExecutorNodeResp extends Resp {

    private List<Node> nodeList;

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
}
