package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.thriftgen.TIdReq;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.timer.TTimeReq;
import com.gsralex.gflow.pub.thriftgen.timer.TTimerService;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.service.TimerService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gsralex
 * @version 2018/12/18
 */
@Component
public class TTimerServiceImpl implements TTimerService.Iface {

    @Autowired
    private TimerService timerService;
    private SchedulerContext context = SchedulerContext.getInstance();
    private String accessKey;

    @Override
    public TResp saveTimer(TTimeReq req) throws TException {
        TResp resp = new TResp();
//        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
//            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
//            return resp;
//        }
        timerService.saveTimer(req.getFlowGroupId(), req.isActive(), req.getTime());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp updateTimer(TTimeReq req) throws TException {
        TResp resp = new TResp();
//        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
//            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
//            return resp;
//        }
        timerService.updateTimer(req.getId(), req.getFlowGroupId(), req.isActive(), req.getTime());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp removeTimer(TIdReq req) throws TException {
        TResp resp = new TResp();
//        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
//            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
//            return resp;
//        }
        timerService.removeTimer(req.getId());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

}