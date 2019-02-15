package com.gsralex.gflow.scheduler.client.config;

import com.gsralex.gflow.pub.util.PropertyName;

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
