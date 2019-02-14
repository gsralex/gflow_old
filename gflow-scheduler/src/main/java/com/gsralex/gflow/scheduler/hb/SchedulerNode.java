package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.core.context.IpAddr;

/**
 * @author gsralex
 * @version 2019/2/14
 */
public class SchedulerNode {

    private IpAddr ip;
    private boolean online;
    private long lastHbTime;
    private boolean master;

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

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }
}
