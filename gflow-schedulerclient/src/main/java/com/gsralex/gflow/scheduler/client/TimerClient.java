package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.pub.action.IdReq;
import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.scheduler.client.action.timer.TimerReq;

/**
 * @author gsralex
 * @version 2019/3/8
 */
public interface TimerClient {

    Resp saveTimer(TimerReq req);

    Resp updateTimer(TimerReq req);

    Resp removeTimer(IdReq req);
}
