package com.gsralex.gflow.scheduler.domain;

import com.gsralex.gdata.bean.annotation.Column;
import com.gsralex.gdata.bean.annotation.Id;
import com.gsralex.gdata.bean.annotation.Table;

/**
 * @author gsralex
 * @date 2018/2/12
 */
@Table(name = "gflow_job")
public class Job {

    @Id
    private Integer id;
    @Column(name = "action_id")
    private Long actionId;
    @Column(name = "flow_group_id")
    private Long flowGroupId;
    @Column(name = "job_group_id")
    private Long jobGroupId;
    @Column(name = "start_time")
    private Long startTime;
    @Column(name = "end_time")
    private Long endTime;
    @Column(name = "retry_cnt")
    private Integer retryCnt;
    @Column(name = "status")
    private Integer status;
    @Column(name = "retry_job_id")
    private Long retryJobId;
    @Column(name = "create_time")
    private Long createTime;
    @Column(name = "mod_time")
    private Long modTime;
    @Column(name = "index")
    private Integer index;
    @Column(name = "parameter")
    private String parameter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getFlowGroupId() {
        return flowGroupId;
    }

    public void setFlowGroupId(Long flowGroupId) {
        this.flowGroupId = flowGroupId;
    }

    public Long getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(Long jobGroupId) {
        this.jobGroupId = jobGroupId;
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
