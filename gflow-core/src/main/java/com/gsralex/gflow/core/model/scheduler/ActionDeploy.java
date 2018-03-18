package com.gsralex.gflow.core.model.scheduler;

import com.gsralex.gdata.annotation.LabelField;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class ActionDeploy {

    @LabelField(name = "action_id")
    private long actionId;
    private String ip;
    private int port;
    @LabelField(name = "class_name")
    private String className;
    private String name;

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
