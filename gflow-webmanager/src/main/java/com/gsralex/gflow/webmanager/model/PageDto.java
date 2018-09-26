package com.gsralex.gflow.webmanager.model;

/**
 * @author gsralex
 * @version 2018/9/5
 */
public class PageDto extends ResultDto {

    private Object data;
    private int cnt;
    private int pageSize;
    private int pageIndex;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

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
