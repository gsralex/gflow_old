package com.gsralex.gflow.core.domain;

import com.gsralex.gdata.bean.annotation.Column;

/**
 * @author gsralex
 * @date 2017/12/25
 */
public class GFlowAction {
    private long id;
    private String name;
    @Column(name = "class_name")
    private String className;
    @Column(name = "create_time")
    private int createTime;
    @Column(name = "mod_time")
    private int modTime;

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

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getModTime() {
        return modTime;
    }

    public void setModTime(int modTime) {
        this.modTime = modTime;
    }
}
