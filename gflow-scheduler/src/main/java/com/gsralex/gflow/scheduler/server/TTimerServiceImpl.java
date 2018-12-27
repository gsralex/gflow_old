package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.timer.TConfigService;
import com.gsralex.gflow.core.thriftgen.timer.TDelTimerReq;
import com.gsralex.gflow.core.thriftgen.timer.TSettingsReq;
import com.gsralex.gflow.core.thriftgen.timer.TTimeReq;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.domain.TimerConfig;
import com.gsralex.gflow.scheduler.enums.ExecuteTimeEnum;
import com.gsralex.gflow.scheduler.sql.TimerDao;
import com.gsralex.gflow.scheduler.sql.impl.TimerDaoImpl;
import com.gsralex.gflow.scheduler.timer.TimerProcessor;
import com.gsralex.gflow.scheduler.timer.TimerService;
import com.gsralex.gflow.scheduler.timer.TimerTask;
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