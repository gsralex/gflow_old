package com.gsralex.gflow.scheduler.model;

/**
 * @author gsralex
 * @version 2018/12/18
 */
public class TimerExecuteRecord {

    private Long timerConfigId;
    private Long createTime;

    public Long getTimerConfigId() {
        return timerConfigId;
    }

    public void setTimerConfigId(Long timerConfigId) {
        this.timerConfigId = timerConfigId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
