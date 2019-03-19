package com.gsralex.gflow.core.domain;

import com.gsralex.gdata.bean.annotation.Column;
import com.gsralex.gdata.bean.annotation.Id;
import com.gsralex.gdata.bean.annotation.Table;

/**
 * @author gsralex
 * @date 2017/12/25
 */
@Table("gflow_flowitem")
public class FlowItemPo {

    @Id
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
    private String label;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
