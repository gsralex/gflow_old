package com.gsralex.gflow.scheduler.retry;

/**
 * @author gsralex
 * @version 2018/8/21
 */
public class RetryTask {
    /**
     * 失败的任务id
     */
    private long retryJobId;
    /**
     * 任务组id
     */
    private long jobGroupId;
    /**
     * 配置组id
     */
    private long groupId;
    /**
     * actionId
     */
    private long actionId;
    /**
     * 在流程组的位置
     */
    private int index;
    /**
     * 携带参数，用于重现
     */
    private String parameter;

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

    private boolean ok;

    public long getRetryJobId() {
        return retryJobId;
    }

    public void setRetryJobId(long retryJobId) {
        this.retryJobId = retryJobId;
    }

    public long getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(long jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
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

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}


