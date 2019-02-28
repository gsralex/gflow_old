package com.gsralex.gflow.web.req;

/**
 * @author gsralex
 * @version 2019/2/26
 */
public class TimerListReq {

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
