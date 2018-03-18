package com.gsralex.gflow.core.domain;

import com.gsralex.gdata.annotation.IdField;
import com.gsralex.gdata.annotation.LabelField;
import com.gsralex.gdata.annotation.TbName;

/**
 * @author gsralex
 * @date 2018/2/12
 */
@TbName(name = "gflow_jobgroup")
public class GFlowJobGroup {
    @IdField
    private long id;
    @LabelField(name = "start_time")
    private int startTime;
    @LabelField(name="end_time")
    private int endTime;
    @LabelField(name="create_time")
    private int createTime;
    @LabelField(name="trigger_group_id")
    private long triggerGroupId;
    @LabelField(name="start_trigger_id")
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
