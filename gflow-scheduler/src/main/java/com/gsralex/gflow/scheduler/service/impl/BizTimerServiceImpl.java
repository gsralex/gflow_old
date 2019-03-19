package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.core.domain.TimerConfigPo;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.dao.TimerDao;
import com.gsralex.gflow.scheduler.service.BizTimerService;
import com.gsralex.gflow.scheduler.timer.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2018/12/26
 */
@Service
public class BizTimerServiceImpl implements BizTimerService {

    @Autowired
    private TimerDao timerDao;
    private SchedulerContext context = SchedulerContext.getInstance();

    @Override
    public boolean saveTimer(long flowGroupId, boolean active, String time) {
        TimerConfigPo config = new TimerConfigPo();
        config.setActive(active);
        config.setCreateTime(System.currentTimeMillis());
        config.setModTime(System.currentTimeMillis());
        config.setDel(false);
        config.setFlowGroupId(flowGroupId);
        config.setTime(time);
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
        TimerConfigPo config = timerDao.getTimer(id);
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
        TimerConfigPo config = timerDao.getTimer(id);
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
