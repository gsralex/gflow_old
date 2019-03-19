package com.gsralex.gflow.executor.config;

import com.gsralex.gflow.core.util.PropertyName;

public class ExecutorConfig {
    @PropertyName(name = "gflow.port")
    private int port;
    @PropertyName(name = "gflow.scheduler.ips")
    private String schedulerIps;
    @PropertyName(name = "gflow.accesskey")
    private String accessKey;
    @PropertyName(name = "gflow.executor.tag")
    private String tag;
    @PropertyName(name = "gflow.executor.threads")
    private int threads;
    @PropertyName(name = "gflow.zk.server")
    private String zkServer;
    @PropertyName(name = "gflow.zk.active")
    private boolean zkActive;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSchedulerIps() {
        return schedulerIps;
    }

    public void setSchedulerIps(String schedulerIps) {
        this.schedulerIps = schedulerIps;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getZkServer() {
        return zkServer;
    }

    public void setZkServer(String zkServer) {
        this.zkServer = zkServer;
    }

    public boolean isZkActive() {
        return zkActive;
    }

    public void setZkActive(boolean zkActive) {
        this.zkActive = zkActive;
    }
}
