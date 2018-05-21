package com.gsralex.gflow.scheduler.rpc;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class JobDesc {

    private long id;
    private String ip;
    private int port;
    private String parameter;
    private long actionId;
    private long jobGroupId;

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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public long getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(long jobGroupId) {
        this.jobGroupId = jobGroupId;
    }
}
