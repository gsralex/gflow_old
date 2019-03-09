package com.gsralex.gflow.scheduler.client.action.timer;

import com.gsralex.gflow.pub.action.Req;

/**
 * @author gsralex
 * @version 2018/12/27
 */
public class TimerReq extends Req {

    private Long id;
    private Long flowGroupId;
    private String time;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlowGroupId() {
        return flowGroupId;
    }

    public void setFlowGroupId(Long flowGroupId) {
        this.flowGroupId = flowGroupId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
