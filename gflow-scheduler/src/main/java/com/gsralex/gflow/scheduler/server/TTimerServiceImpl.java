package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.pub.util.SecurityUtils;
import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.timer.TConfigService;
import com.gsralex.gflow.pub.thriftgen.timer.TDelTimerReq;
import com.gsralex.gflow.pub.thriftgen.timer.TSettingsReq;
import com.gsralex.gflow.pub.thriftgen.timer.TTimeReq;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.timer.TimerService;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/12/18
 */
public class TTimerServiceImpl implements TConfigService.Iface {

    private TimerService timerService;
    private SchedulerContext context;
    private String accessKey;

    public TTimerServiceImpl(SchedulerContext context) {
        timerService = new TimerService(context);
        accessKey = context.getConfig().getAccessKey();
    }

    @Override
    public TResp setSettings(TSettingsReq req) throws TException {
        return null;
    }

    @Override
    public TResp addTimer(TTimeReq req) throws TException {
        TResp resp = new TResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        timerService.saveTimer(req.getFlowGroupId(), req.isActive(), req.getTime());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp updateTimer(TTimeReq req) throws TException {
        TResp resp = new TResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        timerService.updateTimer(req.getId(), req.getFlowGroupId(), req.isActive(), req.getTime());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp delTimer(TDelTimerReq req) throws TException {
        TResp resp = new TResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        timerService.deleteTimer(req.getId());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }
}