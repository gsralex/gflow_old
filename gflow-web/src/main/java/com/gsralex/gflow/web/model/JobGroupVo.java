package com.gsralex.gflow.web.model;

import com.gsralex.gdata.bean.annotation.Column;

/**
 * @author gsralex
 * @version 2018/10/31
 */
public class JobGroupVo {

    private Long id;
    private Integer date;
    private Integer status;
    @Column("start_time")
    private Long startTime;
    @Column("end_time")
    private Long endTime;
    @Column("timer_config_id")
    private Long timerConfigId;
    private Boolean isExecute;
    private String name;
    private String description;
    private String parameter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTimerConfigId() {
        return timerConfigId;
    }

    public void setTimerConfigId(Long timerConfigId) {
        this.timerConfigId = timerConfigId;
    }

    public Boolean getExecute() {
        return isExecute;
    }

    public void setExecute(Boolean execute) {
        isExecute = execute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
