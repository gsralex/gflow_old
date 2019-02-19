package com.gsralex.gflow.executor.hb;

import com.gsralex.gflow.pub.context.IpAddr;

/**
 * @author gsralex
 * @version 2019/2/19
 */
public class SchedulerNode {

    private IpAddr ip;
    private boolean online;

    public IpAddr getIp() {
        return ip;
    }

    public void setIp(IpAddr ip) {
        this.ip = ip;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
