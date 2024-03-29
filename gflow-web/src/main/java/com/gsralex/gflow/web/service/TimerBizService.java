package com.gsralex.gflow.web.service;

import com.gsralex.gflow.scheduler.client.domain.TimerReq;
import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.resp.Resp;
import com.gsralex.gflow.web.resp.ResultResp;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface TimerBizService {


    Resp saveTimer(TimerReq req);

    Resp updateTimer(TimerReq req);

    Resp removeTimer(long id);

    ResultResp getTimer(long id);

    PageResp listTimer(int pageSize, int pageIndex);
}
