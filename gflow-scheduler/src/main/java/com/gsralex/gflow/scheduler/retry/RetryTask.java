package com.gsralex.gflow.scheduler.retry;

import com.gsralex.gflow.scheduler.schedule.ActionDesc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/21
 */
public class RetryTask {

    private long jobId;

    private ActionDesc actionDesc;

    /**
     * 任务超时时间ms
     */
    private long timeoutTime;
    /**
     * 重试的时间（进入队列的时间）
     */
    private long retryTime;
    /**
     * 当前重试次数
     */
    private int retryCnt;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }


    public ActionDesc getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(ActionDesc actionDesc) {
        this.actionDesc = actionDesc;
    }

    public long getTimeoutTime() {
        return timeoutTime;
    }

    public void setTimeoutTime(long timeoutTime) {
        this.timeoutTime = timeoutTime;
    }

    public long getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(long retryTime) {
        this.retryTime = retryTime;
    }

    public int getRetryCnt() {
        return retryCnt;
    }

    public void setRetryCnt(int retryCnt) {
        this.retryCnt = retryCnt;
    }

}


