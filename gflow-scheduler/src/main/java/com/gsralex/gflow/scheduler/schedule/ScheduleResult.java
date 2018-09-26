package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.enums.JobStatusEnum;

/**
 * @author gsralex
 * @version 2018/8/22
 */
public class ScheduleResult {

    private JobStatusEnum status;
    private long groupId;
    private long jobGroupId;
    private long actionId;
    private int index;
    private long jobId;

    public JobStatusEnum getStatus() {
        return status;
    }

    public void setStatus(JobStatusEnum status) {
        this.status = status;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(long jobGroupId) {
        this.jobGroupId = jobGroupId;
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

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }
}