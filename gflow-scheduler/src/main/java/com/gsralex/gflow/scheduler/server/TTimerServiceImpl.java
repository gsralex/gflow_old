package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.timer.TConfigService;
import com.gsralex.gflow.core.thriftgen.timer.TDelTimerReq;
import com.gsralex.gflow.core.thriftgen.timer.TSettingsReq;
import com.gsralex.gflow.core.thriftgen.timer.TTimeReq;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/12/18
 */
public class TTimerServiceImpl implements TConfigService.Iface {
    @Override
    public TResp setSettings(TSettingsReq req) throws TException {
        return null;
    }

    @Override
    public TResp addTimer(TTimeReq req) throws TException {
        return null;
    }

    @Override
    public TResp updateTimer(TTimeReq req) throws TException {
        return null;
    }

    @Override
    public TResp delTimer(TDelTimerReq req) throws TException {
        return null;
    }
}