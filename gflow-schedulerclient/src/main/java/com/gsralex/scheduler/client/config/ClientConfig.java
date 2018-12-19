package com.gsralex.scheduler.client.config;

import com.gsralex.gflow.core.util.PropertyName;

public class ClientConfig {

    @PropertyName(name = "gflow.scheduler.ips")
    private String schedulerIps;
    @PropertyName(name = "gflow.accesskey")
    private String accessKey;

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
