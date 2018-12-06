package com.gsralex.gflow.scheduler.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/17
 */
public class FlowNode {

    private List<FlowNode> pre;

    private List<FlowNode> next;

    private boolean ok;
    private long actionId;
    private int index;
    private boolean schedule;
    /**
     * flow定义的参数
     */
    private String parameter;

    public FlowNode() {
        this.pre = new ArrayList<>();
        this.next = new ArrayList<>();
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

    public List<FlowNode> getPre() {
        return pre;
    }

    public void setPre(List<FlowNode> pre) {
        this.pre = pre;
    }

    public List<FlowNode> getNext() {
        return next;
    }

    public void setNext(List<FlowNode> next) {
        this.next = next;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isSchedule() {
        return schedule;
    }

    public void setSchedule(boolean schedule) {
        this.schedule = schedule;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}