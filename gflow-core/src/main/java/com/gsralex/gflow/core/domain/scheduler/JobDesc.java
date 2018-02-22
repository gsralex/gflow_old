package com.gsralex.gflow.core.domain.scheduler;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public class JobDesc {
    private String parameter;
    private long id;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
