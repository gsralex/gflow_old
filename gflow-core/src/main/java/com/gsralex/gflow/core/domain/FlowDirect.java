package com.gsralex.gflow.core.domain;

import com.gsralex.gdata.bean.annotation.Column;

/**
 * @author gsralex
 * @version 2018/12/7
 */
public class FlowDirect {

    private Long id;
    @Column(name = "pre_index")
    private Integer preIndex;
    private Integer index;
    @Column(name = "group_id")
    private Long groupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPreIndex() {
        return preIndex;
    }

    public void setPreIndex(Integer preIndex) {
        this.preIndex = preIndex;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
