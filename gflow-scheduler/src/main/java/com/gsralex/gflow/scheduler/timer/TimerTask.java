package com.gsralex.gflow.scheduler.timer;


import com.gsralex.gflow.core.domain.TimerConfigPo;

/**
 * @author gsralex
 * @version 2018/8/20
 */
public class TimerTask {

    private long lastExecutionTime;
    private TimerConfigPo config;

    public TimerTask(TimerConfigPo config) {
        this(config, 0);
    }

    public TimerTask(TimerConfigPo config, long lastExecutionTime) {
        this.config = config;
        this.lastExecutionTime = lastExecutionTime;
    }

    public void setTimerConfig(TimerConfigPo config) {
        this.config = config;
    }

    public TimerConfigPo getTimerConfig() {
        return config;
    }

    public long getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(long lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }
}
