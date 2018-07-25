package com.gsralex.gflow.scheduler.domain.flow;

/**
 * @author gsralex
 * @date 2017/12/25
 */
public class GFlowTrigger {

    private long id;
    private long triggerGroupId;
    private long triggerActionId;
    private long actionGroupId;
    private long actionId;
    private int createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTriggerGroupId() {
        return triggerGroupId;
    }

    public void setTriggerGroupId(long triggerGroupId) {
        this.triggerGroupId = triggerGroupId;
    }

    public long getTriggerActionId() {
        return triggerActionId;
    }

    public void setTriggerActionId(long triggerActionId) {
        this.triggerActionId = triggerActionId;
    }

    public long getActionGroupId() {
        return actionGroupId;
    }

    public void setActionGroupId(long actionGroupId) {
        this.actionGroupId = actionGroupId;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }
}
