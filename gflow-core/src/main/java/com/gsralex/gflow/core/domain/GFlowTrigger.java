package com.gsralex.gflow.core.domain;

import com.gsralex.gdata.bean.annotation.Column;

/**
 * @author gsralex
 * @date 2017/12/25
 */
public class GFlowTrigger {

    private long id;
    @Column(name = "pre_group_id")
    private long preGroupId;
    @Column(name = "pre_action_id")
    private long preActionId;
    @Column(name = "pre_index")
    private int preIndex;
    @Column(name = "action_group_id")
    private long actionGroupId;
    @Column(name = "action_id")
    private long actionId;
    @Column(name = "index")
    private int index;
    @Column(name = "create_time")
    private Long createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPreGroupId() {
        return preGroupId;
    }

    public void setPreGroupId(long preGroupId) {
        this.preGroupId = preGroupId;
    }

    public long getPreActionId() {
        return preActionId;
    }

    public void setPreActionId(long preActionId) {
        this.preActionId = preActionId;
    }

    public int getPreIndex() {
        return preIndex;
    }

    public void setPreIndex(int preIndex) {
        this.preIndex = preIndex;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
