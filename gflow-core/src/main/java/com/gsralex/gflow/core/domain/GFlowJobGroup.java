package com.gsralex.gflow.core.domain;

import com.gsralex.gflow.core.dao.helper.annotation.AliasField;
import com.gsralex.gflow.core.dao.helper.annotation.IdField;
import com.gsralex.gflow.core.dao.helper.annotation.TbName;

/**
 * @author gsralex
 * @date 2018/2/12
 */
@TbName(name = "gflow_jobgroup")
public class GFlowJobGroup {
    @IdField
    private long id;
    @AliasField(name = "start_time")
    private int startTime;
    @AliasField(name="end_time")
    private int endTime;
    @AliasField(name="create_time")
    private int createTime;
    @AliasField(name="trigger_group_id")
    private long triggerGroupId;
    @AliasField(name="start_trigger_id")
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
