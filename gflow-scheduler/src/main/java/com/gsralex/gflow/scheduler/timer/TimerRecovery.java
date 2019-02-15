package com.gsralex.gflow.scheduler.timer;

import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.domain.TimerConfig;
import com.gsralex.gflow.scheduler.model.TimerExecuteRecord;
import com.gsralex.gflow.scheduler.dao.JobDao;
import com.gsralex.gflow.scheduler.dao.TimerDao;
import com.gsralex.gflow.scheduler.dao.impl.JobDaoImpl;
import com.gsralex.gflow.scheduler.dao.impl.TimerDaoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务恢复
 *
 * @author gsralex
 * @version 2018/12/18
 */
public class TimerRecovery {

    private TimerProcess processor;
    private JobDao jobDao;
    private TimerDao timerDao;

    public TimerRecovery(TimerProcess processor, SchedulerContext context) {
        this.processor = processor;
        jobDao = new JobDaoImpl(context.getJdbcUtils());
        timerDao = new TimerDaoImpl(context.getJdbcUtils());
    }

    public void recovery() {
        List<TimerConfig> timerList = timerDao.listTimer();
        List<Long> timerIdList = timerList.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<TimerExecuteRecord> execList = jobDao.listJobGroupExec(timerIdList);
        Map<Long, Long> execMap = new HashMap<>();
        for (TimerExecuteRecord exec : execList) {
            execMap.put(exec.getTimerConfigId(), exec.getCreateTime());
        }
        for (TimerConfig timer : timerList) {
            Long execTime = execMap.get(timer.getId());
            if (execTime == null)
                execTime = 0L;
            TimerTask timerTask = new TimerTask(timer, execTime);
            processor.setTimer(timerTask);
        }
    }
}
