package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.core.context.IpAddr;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class ExecutorNode {
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
