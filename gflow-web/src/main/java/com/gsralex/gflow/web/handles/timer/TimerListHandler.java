package com.gsralex.gflow.web.handles.timer;

import com.gsralex.gflow.web.handles.HttpHandler;
import com.gsralex.gflow.web.req.TimerListReq;
import com.gsralex.gflow.web.resp.TimerListResp;

/**
 * @author gsralex
 * @version 2019/2/26
 */
public class TimerListHandler implements HttpHandler<TimerListReq, TimerListResp> {

    public TimerListHandler() {
    }

    @Override
    public TimerListResp doAction(TimerListReq req) {
        TimerListResp resp = new TimerListResp();
        resp.setPageIndex(req.getPageIndex());
        resp.setPageSize(req.getPageSize());
        return resp;
    }

    @Override
    public String getRequestPath() {
        return "/timer/list";
    }
}
