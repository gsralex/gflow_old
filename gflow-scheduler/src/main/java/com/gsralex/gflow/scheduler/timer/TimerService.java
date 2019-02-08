package com.gsralex.gflow.scheduler.timer;

import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.domain.TimerConfig;
import com.gsralex.gflow.scheduler.enums.ExecuteTimeEnum;
import com.gsralex.gflow.scheduler.dao.TimerDao;
import com.gsralex.gflow.scheduler.dao.impl.TimerDaoImpl;

/**
 * @author gsralex
 * @version 2018/12/26
 */
public class TimerService {

    private TimerDao timerDao;

    public TimerService(SchedulerContext context) {
        timerDao = new TimerDaoImpl(context.getJdbcUtils());
    }

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
            TimerProcessor.getInstance().setTimer(timerTask);
        }
        return true;
    }

    public boolean updateTimer(long id, long flowGroupId, boolean active, String time) {
        TimerConfig config = timerDao.getTimer(id);
        if (config != null) {
            config.setTime(time);
            config.setActive(active);
            config.setModTime(System.currentTimeMillis());
            config.setFlowGroupId(flowGroupId);
            timerDao.updateTimer(config);

            TimerTask timerTask = new TimerTask(config);
            TimerProcessor.getInstance().setTimer(timerTask);
            return true;
        }
        return false;
    }

    public boolean deleteTimer(long id) {
        return timerDao.deleteTimer(id);
    }
}
