package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.domain.GFlowJob;

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
