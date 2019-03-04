package com.gsralex.gflow.scheduler.client.action.action;

import com.gsralex.gflow.pub.action.Req;

/**
 * @author gsralex
 * @version 2019/3/3
 */
public class ActionListReq extends Req {

    private int pageSize;
    private int pageIndex;

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
}
