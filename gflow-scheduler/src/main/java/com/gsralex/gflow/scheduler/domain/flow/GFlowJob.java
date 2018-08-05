package com.gsralex.gflow.scheduler.domain.flow;

import com.gsralex.gdata.bean.annotation.Id;
import com.gsralex.gflow.core.util.PropertyName;

/**
 * @author gsralex
 * @date 2018/2/12
 */
public class GFlowJob {

    @Id
    private long id;
    @PropertyName(name = "action_id")
    private long actionId;
    @PropertyName(name = "trigger_group_id")
    private long triggerGroupId;
    @PropertyName(name = "start_time")
    private int startTime;
    @PropertyName(name = "end_time")
    private int endTime;
    @PropertyName(name = "err_msg")
    private String errMsg;
    @PropertyName(name = "retry_cnt")
    private int retryCnt;
    @PropertyName(name = "result")
    private int result;
    @PropertyName(name = "retry_job_id")
    private long retryJobId;
    @PropertyName(name = "create_time")
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

    public int getRetryCnt() {
        return retryCnt;
    }

    public void setRetryCnt(int retryCnt) {
        this.retryCnt = retryCnt;
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
