package com.gsralex.gflow.scheduler.client.action.action;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/3
 */
public class ActionListResp {

    private List<Action> actionList;
    private int cnt;

    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
