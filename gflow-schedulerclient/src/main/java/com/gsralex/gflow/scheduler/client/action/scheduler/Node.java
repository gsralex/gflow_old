package com.gsralex.gflow.scheduler.client.action.scheduler;

import com.gsralex.gflow.pub.context.IpAddr;

/**
 * @author gsralex
 * @version 2019/3/12
 */
public class Node {

    private IpAddr ip;
    private String tag;

    public IpAddr getIp() {
        return ip;
    }

    public void setIp(IpAddr ip) {
        this.ip = ip;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
