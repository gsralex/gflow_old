package com.gsralex.gflow.core.domain;

import com.gsralex.gdata.bean.annotation.Column;
import com.gsralex.gdata.bean.annotation.Id;
import com.gsralex.gdata.bean.annotation.Table;

/**
 * @author gsralex
 * @date 2018/2/12
 */
@Table("gflow_job")
public class JobPo {

    @Id
    private Integer id;
    @Column("action_id")
    private Long actionId;
    @Column("flow_group_id")
    private Long flowGroupId;
    @Column("job_group_id")
    private Long jobGroupId;
    @Column("start_time")
    private Long startTime;
    @Column("end_time")
    private Long endTime;
    @Column("retry_cnt")
    private Integer retryCnt;
    @Column("status")
    private Integer status;
    @Column("retry_job_id")
    private Long retryJobId;
    @Column("create_time")
    private Long createTime;
    @Column("mod_time")
    private Long modTime;
    @Column("index")
    private Integer index;
    @Column("parameter")
    private String parameter;
    @Column("execute_server")
    private String executerServer;
    @Column("schedule_server")
    private String scheduleServer;

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

    public String getExecuterServer() {
        return executerServer;
    }

    public void setExecuterServer(String executerServer) {
        this.executerServer = executerServer;
    }

    public String getScheduleServer() {
        return scheduleServer;
    }

    public void setScheduleServer(String scheduleServer) {
        this.scheduleServer = scheduleServer;
    }
}
