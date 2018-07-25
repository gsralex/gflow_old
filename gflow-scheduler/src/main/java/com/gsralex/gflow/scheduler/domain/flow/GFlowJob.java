package com.gsralex.gflow.scheduler.domain.flow;

/**
 * @author gsralex
 * @date 2018/2/12
 */
public class GFlowJob {

    private long id;
    private long actionId;
    private long triggerGroupId;
    private int startTime;
    private int endTime;
    private String errMsg;
    private int sendRetryCnt;
    private int actionRetryCnt;
    private int result;
    private long retryJobId;
    private int createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public long getTriggerGroupId() {
        return triggerGroupId;
    }

    public void setTriggerGroupId(long triggerGroupId) {
        this.triggerGroupId = triggerGroupId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getSendRetryCnt() {
        return sendRetryCnt;
    }

    public void setSendRetryCnt(int sendRetryCnt) {
        this.sendRetryCnt = sendRetryCnt;
    }

    public int getActionRetryCnt() {
        return actionRetryCnt;
    }

    public void setActionRetryCnt(int actionRetryCnt) {
        this.actionRetryCnt = actionRetryCnt;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getRetryJobId() {
        return retryJobId;
    }

    public void setRetryJobId(long retryJobId) {
        this.retryJobId = retryJobId;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }
}
