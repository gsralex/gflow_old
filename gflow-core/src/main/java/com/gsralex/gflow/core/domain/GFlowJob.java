package com.gsralex.gflow.core.domain;

import com.gsralex.gdata.bean.annotation.Column;
import com.gsralex.gdata.bean.annotation.Id;
import com.gsralex.gdata.bean.annotation.Table;

/**
 * @author gsralex
 * @date 2018/2/12
 */
@Table(name = "gflow_job")
public class GFlowJob {

    @Id
    private Integer id;
    @Column(name = "action_id")
    private Long actionId;
    @Column(name = "trigger_group_id")
    private Long triggerGroupId;
    @Column(name = "job_group_id")
    private Long jobGroupId;
    @Column(name = "start_time")
    private Integer startTime;
    @Column(name = "end_time")
    private Integer endTime;
    @Column(name = "err_msg")
    private String errMsg;
    @Column(name = "retry_cnt")
    private Integer retryCnt;
    @Column(name = "status")
    private Integer status;
    @Column(name = "retry_job_id")
    private Long retryJobId;
    @Column(name = "create_time")
    private Integer createTime;
    @Column(name = "index")
    private Integer index;

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

    public Long getTriggerGroupId() {
        return triggerGroupId;
    }

    public void setTriggerGroupId(Long triggerGroupId) {
        this.triggerGroupId = triggerGroupId;
    }

    public Long getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(Long jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}