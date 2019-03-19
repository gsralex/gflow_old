package com.gsralex.gflow.web.resp;



/**
 * @author gsralex
 * @version 2019/3/8
 */
public class PageResp extends Resp {

    private int pageIndex;
    private int pageSize;
    private Object data;
    private int cnt;


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

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
}
