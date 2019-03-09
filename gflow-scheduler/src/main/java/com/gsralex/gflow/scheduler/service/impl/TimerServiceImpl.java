package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.pub.domain.TimerConfig;
import com.gsralex.gflow.pub.enums.ExecuteTimeEnum;
import com.gsralex.gflow.scheduler.SchedulerContext;
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
            } else {

            }
        }
        return true;
    }

    @Override
    public boolean updateTimer(long id, long flowGroupId, boolean active, String time) {
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
