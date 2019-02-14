package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.core.context.IpAddr;

/**
 * @author gsralex
 * @version 2019/2/13
 */
public class ExecutorNode {

    private IpAddr ip;
    private boolean online;
    //上次心跳时间
    private long lastHbTime;

    private String tag;

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

    public long getLastHbTime() {
        return lastHbTime;
    }

    public void setLastHbTime(long lastHbTime) {
        this.lastHbTime = lastHbTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}


