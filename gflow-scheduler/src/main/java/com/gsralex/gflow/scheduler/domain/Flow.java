package com.gsralex.gflow.scheduler.domain;

import com.gsralex.gdata.bean.annotation.Column;

/**
 * @author gsralex
 * @date 2017/12/25
 */
public class Flow {

    private long id;
    @Column("action_group_id")
    private long actionGroupId;
    @Column("action_id")
    private long actionId;
    @Column("index")
    private int index;
    @Column("create_time")
    private Long createTime;
    private String parameter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
