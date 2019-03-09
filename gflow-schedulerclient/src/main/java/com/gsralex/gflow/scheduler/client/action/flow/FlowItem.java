package com.gsralex.gflow.scheduler.client.action.flow;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public class FlowItem {

    private String parameter;
    private String label;
    private int index;
    private long actionId;

    public FlowItem actionId(long actionId) {
        this.actionId = actionId;
        return this;
    }

    public FlowItem parameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public FlowItem label(String label) {
        this.label = label;
        return this;
    }

    public FlowItem index(int index) {
        this.index = index;
        return this;
    }
}
