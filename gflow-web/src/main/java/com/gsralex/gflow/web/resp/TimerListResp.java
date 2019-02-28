package com.gsralex.gflow.web.resp;

/**
 * @author gsralex
 * @version 2019/2/26
 */
public class TimerListResp {

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
