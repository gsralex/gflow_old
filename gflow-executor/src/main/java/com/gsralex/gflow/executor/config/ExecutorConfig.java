package com.gsralex.gflow.executor.config;

import com.gsralex.gflow.core.util.PropertyName;

public class ExecutorConfig {

    @PropertyName(name = "gflow.port")
    private int port;
    @PropertyName(name = "gflow.scheduler.ips")
    private String schedulerIps;
    @PropertyName(name = "gflow.accesskey")
    private String accessKey;

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
}
