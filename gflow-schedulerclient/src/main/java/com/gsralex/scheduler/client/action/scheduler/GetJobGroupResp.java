package com.gsralex.scheduler.client.action.scheduler;

import com.gsralex.scheduler.client.action.Resp;
import com.gsralex.gflow.scheduler.enums.JobGroupStatusEnum;

/**
 * @author gsralex
 * @version 2018/12/27
 */
public class GetJobGroupResp extends Resp {

    private long jobGroupId;

    private JobGroupStatusEnum status;

    public long getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(long jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public JobGroupStatusEnum getStatus() {
        return status;
    }

    public void setStatus(JobGroupStatusEnum status) {
        this.status = status;
    }
}
