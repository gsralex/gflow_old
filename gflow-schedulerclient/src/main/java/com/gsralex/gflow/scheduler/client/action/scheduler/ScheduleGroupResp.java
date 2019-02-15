package com.gsralex.gflow.scheduler.client.action.scheduler;

import com.gsralex.gflow.pub.action.Resp;

/**
 * @author gsralex
 * @version 2018/12/27
 */
public class ScheduleGroupResp extends Resp {

    private long jobGroupId;

    public long getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(long jobGroupId) {
        this.jobGroupId = jobGroupId;
    }
}
