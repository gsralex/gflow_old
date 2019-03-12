package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.pub.action.IdReq;
import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.domain.TimerConfig;
import com.gsralex.gflow.pub.enums.ExecuteTimeEnum;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.TimerClient;
import com.gsralex.gflow.scheduler.client.action.timer.TimerReq;
import com.gsralex.gflow.scheduler.dao.TimerDao;
import com.gsralex.gflow.scheduler.service.TimerService;
import com.gsralex.gflow.scheduler.timer.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2018/12/26
 */
@Service
public class TimerServiceImpl implements TimerService {

    @Autowired
    private TimerDao timerDao;
    private SchedulerContext context = SchedulerContext.getInstance();

    @Override
    public boolean saveTimer(long flowGroupId, boolean active, String time) {
        //转发master定时处理
        if (!context.isMaster()) {
            TimerClient client = SchedulerClientFactory.createTimer(context.getMasterIp(), "");
            TimerReq req = new TimerReq();
            req.setFlowGroupId(flowGroupId);
            req.setActive(active);
            req.setTime(time);
            Resp resp = client.saveTimer(req);
            return resp.getCode() == ErrConstants.OK ? true : false;
        }
        TimerConfig config = new TimerConfig();
        config.setActive(active);
        config.setCreateTime(System.currentTimeMillis());
        config.setModTime(System.currentTimeMillis());
        config.setDel(false);
        config.setFlowGroupId(flowGroupId);
        config.setTime(time);
        config.setTimeType(ExecuteTimeEnum.Time.getValue());
        timerDao.saveTimer(config);
        if (config.getActive()) {
            TimerTask timerTask = new TimerTask(config);
            if (context.getTimerProcess() != null) {
                context.getTimerProcess().setTimer(timerTask);
            }
        }
        return true;
    }

    @Override
    public boolean updateTimer(long id, long flowGroupId, boolean active, String time) {
        //转发master定时处理
        if (!context.isMaster()) {
            TimerClient client = SchedulerClientFactory.createTimer(context.getMasterIp(), "");
            TimerReq req = new TimerReq();
            req.setId(id);
            req.setActive(active);
            req.setFlowGroupId(flowGroupId);
            req.setTime(time);
            Resp resp = client.updateTimer(req);
            return resp.getCode() == ErrConstants.OK ? true : false;
        }
        TimerConfig config = timerDao.getTimer(id);
        if (config != null) {
            config.setTime(time);
            config.setActive(active);
            config.setModTime(System.currentTimeMillis());
            config.setFlowGroupId(flowGroupId);
            timerDao.updateTimer(config);
            TimerTask timerTask = new TimerTask(config);
            if (context.getTimerProcess() != null) {
                context.getTimerProcess().setTimer(timerTask);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTimer(long id) {
        //转发master定时处理
        if (!context.isMaster()) {
            TimerClient client = SchedulerClientFactory.createTimer(context.getMasterIp(), "");
            IdReq req = new IdReq();
            req.setId(id);
            Resp resp = client.removeTimer(req);
            return resp.getCode() == ErrConstants.OK ? true : false;
        }
        TimerConfig config = timerDao.getTimer(id);
        if (config != null) {
            boolean ok = timerDao.deleteTimer(id);
            if (context.getTimerProcess() != null) {
                context.getTimerProcess().removeTimer(id);
            }
            return ok;
        }
        return false;
    }
}
