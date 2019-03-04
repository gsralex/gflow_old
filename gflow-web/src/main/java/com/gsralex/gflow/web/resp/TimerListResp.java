package com.gsralex.gflow.web.resp;

import com.gsralex.gflow.scheduler.client.action.action.Action;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/26
 */
public class TimerListResp {

    private int pageSize;
    private int pageIndex;
    private List<Action> actionList;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }
}
