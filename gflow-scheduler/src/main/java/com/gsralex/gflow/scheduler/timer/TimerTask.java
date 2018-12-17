package com.gsralex.gflow.scheduler.timer;


import com.gsralex.gflow.scheduler.domain.TimerConfig;

/**
 * @author gsralex
 * @version 2018/8/20
 */
public class TimerTask {

    private long lastExecutionTime;
    private TimerConfig config;

    public TimerTask(TimerConfig config) {
        this(config, 0);
    }

    public TimerTask(TimerConfig config, long lastExecutionTime) {
        this.config = config;
        this.lastExecutionTime = lastExecutionTime;
    }

    public void setTimerConfig(TimerConfig config) {
        this.config = config;
    }

    public TimerConfig getTimerConfig() {
        return config;
    }

    public long getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(long lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }
}
