package com.gsralex.gflow.scheduler.server;

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
import com.gsralex.gflow.scheduler.timer.TimerTask;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/12/18
 */
public class TTimerServiceImpl implements TConfigService.Iface {

    private TimerDao timerDao;
    private SchedulerContext context;
    private ScheduleChain chain;

    public TTimerServiceImpl(SchedulerContext context) {
        timerDao = new TimerDaoImpl(context.getJdbcUtils());
        this.context = context;
        chain = new ScheduleChain(context.getConfig().getAccessKey());
    }

    @Override
    public TResp setSettings(TSettingsReq req) throws TException {
        return null;
    }

    @Override
    public TResp addTimer(TTimeReq req) throws TException {

        class AddTimerCallback implements ScheduleCallback {
            @Override
            public TResp doSchedule() {
                TimerConfig config = new TimerConfig();
                config.setActive(req.isActive());
                config.setCreateTime(System.currentTimeMillis());
                config.setModTime(System.currentTimeMillis());
                config.setDel(false);
                config.setFlowGroupId(req.getFlowGroupId());
                config.setTime(req.getTime());
                config.setTimeType(ExecuteTimeEnum.Time.getValue());
                timerDao.saveTimer(config);
                if (config.getActive()) {
                    TimerTask timerTask = new TimerTask(config);
                    TimerProcessor.getInstance().setTimer(timerTask);
                }
                TResp resp = new TResp();
                return resp;
            }
        }
        return chain.execute(new AddTimerCallback(), req.getAccessToken());
    }

    @Override
    public TResp updateTimer(TTimeReq req) throws TException {

        class UpdateTimerCallback implements ScheduleCallback{
            @Override
            public TResp doSchedule() {
                TResp resp = new TResp();
                TimerConfig config = timerDao.getTimer(req.getId());
                if (config != null) {
                    config.setTime(req.getTime());
                    config.setActive(req.isActive());
                    config.setModTime(System.currentTimeMillis());
                    config.setFlowGroupId(req.getFlowGroupId());
                    timerDao.updateTimer(config);

                    TimerTask timerTask = new TimerTask(config);
                    TimerProcessor.getInstance().setTimer(timerTask);
                } else {
                    resp.setCode(-1);
                }
                return resp;
            }
        }
        return chain.execute(new UpdateTimerCallback(),req.getAccessToken());
    }

    @Override
    public TResp delTimer(TDelTimerReq req) throws TException {
        class DelTimerCallback implements ScheduleCallback{

            @Override
            public TResp doSchedule() {
                TResp resp = new TResp();
                if (timerDao.deleteTimer(req.getId())) {
                    TimerProcessor.getInstance().removeTimer(req.getId());
                }
                return resp;
            }
        }
        return chain.execute(new DelTimerCallback(),req.getAccessToken());
    }
}