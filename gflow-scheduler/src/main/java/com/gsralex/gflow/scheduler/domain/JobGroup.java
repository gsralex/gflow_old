package com.gsralex.gflow.scheduler.domain;


import com.gsralex.gdata.bean.annotation.Column;
import com.gsralex.gdata.bean.annotation.Id;
import com.gsralex.gdata.bean.annotation.Table;

/**
 * @author gsralex
 * @date 2018/2/12
 */
@Table("gflow_jobgroup")
public class JobGroup {
    @Id
    private Long id;
    @Column("start_time")
    private Long startTime;
    @Column("end_time")
    private Long endTime;
    @Column("create_time")
    private Long createTime;
    @Column("flow_group_id")
    private Long flowGroupId;
    @Column("start_flow_id")
    private Long startFlowId;
    @Column("date")
    private Integer date;
    @Column("status")
    private Integer status;
    @Column("timer_config_id")
    private Long timerConfigId;
    private String parameter;

    private String scheduleServer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getFlowGroupId() {
        return flowGroupId;
    }

    public void setFlowGroupId(Long flowGroupId) {
        this.flowGroupId = flowGroupId;
    }

    public Long getStartFlowId() {
        return startFlowId;
    }

    public void setStartFlowId(Long startFlowId) {
        this.startFlowId = startFlowId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimerConfigId() {
        return timerConfigId;
    }

    public void setTimerConfigId(Long timerConfigId) {
        this.timerConfigId = timerConfigId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getScheduleServer() {
        return scheduleServer;
    }

    public void setScheduleServer(String scheduleServer) {
        this.scheduleServer = scheduleServer;
    }
}
