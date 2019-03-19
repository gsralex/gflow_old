package com.gsralex.gflow.scheduler.schedule;


import com.gsralex.gflow.core.enums.JobStatus;

/**
 * @author gsralex
 * @version 2018/8/22
 */
public class ActionResult {

    private JobStatus status;
    private ActionDesc actionDesc;
    private long jobId;

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
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