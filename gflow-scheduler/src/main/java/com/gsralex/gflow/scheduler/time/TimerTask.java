package com.gsralex.gflow.scheduler.time;

import com.gsralex.gflow.core.model.ExecuteConfig;

/**
 * @author gsralex
 * @version 2018/8/20
 */
public class TimerTask {

    private long lastExecutionTime;
    private ExecuteConfig config;

    public TimerTask(ExecuteConfig config) {
        this.config = config;
    }

    public void setExecuteConfig(ExecuteConfig config) {
        this.config = config;
    }

    public ExecuteConfig getConfig() {
        return config;
    }

    public long getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(long lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }
}
