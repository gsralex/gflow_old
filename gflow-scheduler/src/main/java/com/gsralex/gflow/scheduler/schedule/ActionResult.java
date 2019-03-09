package com.gsralex.gflow.scheduler.schedule;


import com.gsralex.gflow.pub.enums.JobStatusEnum;

/**
 * @author gsralex
 * @version 2018/8/22
 */
public class ActionResult {

    private JobStatusEnum status;
    private ActionDesc actionDesc;
    private long jobId;

    public JobStatusEnum getStatus() {
        return status;
    }

    public void setStatus(JobStatusEnum status) {
        this.status = status;
    }

    public ActionDesc getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(ActionDesc actionDesc) {
        this.actionDesc = actionDesc;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }
}