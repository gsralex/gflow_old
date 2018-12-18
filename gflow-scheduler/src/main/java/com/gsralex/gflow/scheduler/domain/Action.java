package com.gsralex.gflow.scheduler.domain;

import com.gsralex.gdata.bean.annotation.Column;

/**
 * @author gsralex
 * @date 2017/12/25
 */
public class Action {
    private long id;
    private String name;
    @Column("class_name")
    private String className;
    @Column("create_time")
    private Long createTime;
    @Column("mod_time")
    private Long modTime;
    @Column("tag_id")
    private Long tagId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
