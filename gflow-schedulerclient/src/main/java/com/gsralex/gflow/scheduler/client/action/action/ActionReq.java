package com.gsralex.gflow.scheduler.client.action.action;

import com.gsralex.gflow.pub.action.Req;

/**
 * @author gsralex
 * @version 2019/3/4
 */
public class ActionReq extends Req {

    private Long id;
    private String name;
    private String className;
    private String tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
