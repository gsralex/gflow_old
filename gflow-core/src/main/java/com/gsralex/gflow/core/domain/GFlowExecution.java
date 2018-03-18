package com.gsralex.gflow.core.domain;


import com.gsralex.gdata.annotation.IdField;
import com.gsralex.gdata.annotation.LabelField;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public class GFlowExecution {

    @IdField
    private long id;
    @LabelField(name = "trigger_group_id")
    private long triggerGroupId;
    private int type;
    private int interval;
    private String time;
    private int week;
    private int month;
    private int active;
    private int timeType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTriggerGroupId() {
        return triggerGroupId;
    }

    public void setTriggerGroupId(long triggerGroupId) {
        this.triggerGroupId = triggerGroupId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }
}
