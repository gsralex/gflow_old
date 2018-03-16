package com.gsralex.gflow.scheduler;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public class JobDesc {
    private long id;
    private String parameter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
