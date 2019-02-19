package com.gsralex.gflow.scheduler.client.action.scheduler;

import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.context.IpAddr;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/19
 */
public class NodeResp extends Resp {

    private List<IpAddr> nodeList;

    public List<IpAddr> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<IpAddr> nodeList) {
        this.nodeList = nodeList;
    }
}
