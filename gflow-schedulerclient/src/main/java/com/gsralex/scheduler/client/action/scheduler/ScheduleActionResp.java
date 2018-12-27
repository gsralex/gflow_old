package com.gsralex.scheduler.client.action.scheduler;

import com.gsralex.scheduler.client.action.Resp;

/**
 * @author gsralex
 * @version 2018/12/27
 */
public class ScheduleActionResp extends Resp {

    private long jobId;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }
}
