package com.gsralex.gflow.scheduler.client.action.scheduler;

import com.gsralex.gflow.scheduler.client.action.Req;

/**
 * @author gsralex
 * @version 2018/12/27
 */
public class ScheduleGroupReq extends Req {

    private long flowGroupId;
    private String parameter;

    public long getFlowGroupId() {
        return flowGroupId;
    }

    public void setFlowGroupId(long flowGroupId) {
        this.flowGroupId = flowGroupId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
