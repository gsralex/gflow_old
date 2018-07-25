package com.gsralex.gflow.scheduler.domain.flow;


import com.gsralex.gdata.bean.annotation.Column;
import com.gsralex.gdata.bean.annotation.Id;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public class GFlowExecuteConfig {

    @Id
    private Long id;
    @Column(name = "trigger_group_id")
    private Long triggerGroupId;
    @Column(name = "time_type")
    private Integer TimeType;
    @Column(name = "interval")
    private Integer interval;
    private String time;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTriggerGroupId() {
        return triggerGroupId;
    }

    public void setTriggerGroupId(Long triggerGroupId) {
        this.triggerGroupId = triggerGroupId;
    }

    public Integer getTimeType() {
        return TimeType;
    }

    public void setTimeType(Integer timeType) {
        TimeType = timeType;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
