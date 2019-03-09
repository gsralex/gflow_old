package com.gsralex.gflow.web.model;

import com.gsralex.gdata.bean.annotation.Column;

/**
 * @author gsralex
 * @version 2018/11/2
 */
public class JobVo {

    private Long id;
    @Column("action_id")
    private Long actionId;
    @Column("start_time")
    private Long startTime;
    @Column("end_time")
    private Long endTime;
    @Column("retry_cnt")
    private Integer retryCnt;
    private Integer status;
    @Column("retry_job_id")
    private Long retryJobId;
    private Integer index;
    private String name;
    @Column("class_name")
    private String className;
    private String parameter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getRetryCnt() {
        return retryCnt;
    }

    public void setRetryCnt(Integer retryCnt) {
        this.retryCnt = retryCnt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRetryJobId() {
        return retryJobId;
    }

    public void setRetryJobId(Long retryJobId) {
        this.retryJobId = retryJobId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
