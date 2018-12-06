package com.gsralex.gflow.scheduler.schedule;

/**
 * @author gsralex
 * @version 2018/10/25
 */
public class ActionDesc {

    private long jobGroupId;
    private long triggerGroupId;
    private long actionId;
    private int index;
    private String parameter;
    /**
     * 组参数，当组任务参数与任务参数的key发生一样的时候，以任务的参数优先
     */
    private String groupParameter;
    private long retryJobId;

    public long getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(long jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public long getTriggerGroupId() {
        return triggerGroupId;
    }

    public void setTriggerGroupId(long triggerGroupId) {
        this.triggerGroupId = triggerGroupId;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getGroupParameter() {
        return groupParameter;
    }

    public void setGroupParameter(String groupParameter) {
        this.groupParameter = groupParameter;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public long getRetryJobId() {
        return retryJobId;
    }

    public void setRetryJobId(long retryJobId) {
        this.retryJobId = retryJobId;
    }
}
