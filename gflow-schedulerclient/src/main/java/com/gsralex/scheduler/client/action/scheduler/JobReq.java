package com.gsralex.scheduler.client.action.scheduler;

/**
 * @author gsralex
 * @version 2018/12/27
 */
public class JobReq {
    private long id;
    private long actionId;
    private long jobGroupId;
    private String className;
    private String parameter;
    private int index;
    private long retryJobId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getRetryJobId() {
        return retryJobId;
    }

    public void setRetryJobId(long retryJobId) {
        this.retryJobId = retryJobId;
    }
}
