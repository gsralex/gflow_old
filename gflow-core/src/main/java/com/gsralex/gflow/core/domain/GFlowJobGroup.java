package com.gsralex.gflow.core.domain;

/**
 * @author gsralex
 * @date 2018/2/12
 */
public class GFlowJobGroup {
    private long id;
    private int startTime;
    private int endTime;
    private int createTime;
    private long triggerGroupId;
    private long startTriggerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public long getTriggerGroupId() {
        return triggerGroupId;
    }

    public void setTriggerGroupId(long triggerGroupId) {
        this.triggerGroupId = triggerGroupId;
    }

    public long getStartTriggerId() {
        return startTriggerId;
    }

    public void setStartTriggerId(long startTriggerId) {
        this.startTriggerId = startTriggerId;
    }
}
