package com.gsralex.gflow.core.domain;


import com.gsralex.gdata.bean.annotation.Column;
import com.gsralex.gdata.bean.annotation.Id;
import com.gsralex.gdata.bean.annotation.Table;

/**
 * @author gsralex
 * @date 2018/2/12
 */
@Table(name = "gflow_jobgroup")
public class GFlowJobGroup {
    @Id
    private Long id;
    @Column(name = "start_time")
    private Integer startTime;
    @Column(name="end_time")
    private Integer endTime;
    @Column(name="create_time")
    private Integer createTime;
    @Column(name="trigger_group_id")
    private Long triggerGroupId;
    @Column(name="start_trigger_id")
    private Long startTriggerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Long getTriggerGroupId() {
        return triggerGroupId;
    }

    public void setTriggerGroupId(Long triggerGroupId) {
        this.triggerGroupId = triggerGroupId;
    }

    public Long getStartTriggerId() {
        return startTriggerId;
    }

    public void setStartTriggerId(Long startTriggerId) {
        this.startTriggerId = startTriggerId;
    }
}
