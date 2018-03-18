package com.gsralex.gflow.core.domain;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class GFlowDeploy {

    public enum DeployEnum{
        Scheduler,Executor;
    }

    private long id;
    private String ip;
    private int port;
    private int type;
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

