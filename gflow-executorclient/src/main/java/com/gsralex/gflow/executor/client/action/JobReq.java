package com.gsralex.gflow.executor.client.action;

/**
 * @author gsralex
 * @version 2019/2/8
 */
public class JobReq extends BaseReq {

    private long id; // required
    private long actionId; // required
    private long jobGroupId; // required
    private String className; // required
    private String parameter; // required
    private int index; // required
    private long retryJobId; // required

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
