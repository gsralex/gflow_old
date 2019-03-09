package com.gsralex.gflow.scheduler.dao;

/**
 * @author gsralex
 * @version 2019/3/8
 */
public class TimerExecuteRecord {

    private long timerConfigId;
    private long createTime;

    public long getTimerConfigId() {
        return timerConfigId;
    }

    public void setTimerConfigId(long timerConfigId) {
        this.timerConfigId = timerConfigId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
