package com.gsralex.gflow.scheduler.client.action.scheduler;

import com.gsralex.gflow.core.action.Resp;

/**
 * @author gsralex
 * @version 2018/12/27
 */
public class JobResp extends Resp {

    private long jobId;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }
}
