package com.gsralex.gflow.scheduler.time;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.enums.ExecuteTimeEnum;
import com.gsralex.gflow.core.model.ExecuteConfig;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/8/21
 */
@Component
public class TimerTaskProcessor {

    private Map<Long, TimerTask> map = new HashMap<>();

    private final Object lock = new Object();
    @Autowired
    private ScheduleLinkHandle scheduleLinkHandle;

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLoop();
            }
        }).start();
    }

    public void update(ExecuteConfig config) {
        synchronized (map) {
            if (map.containsKey(config.getId())) {
                TimerTask timerTask = map.get(config.getId());
                timerTask.setExecuteConfig(config);
            } else {
                map.put(config.getId(), new TimerTask(config));
            }
            map.notify();
        }
    }

    private TimerTask getMin() {
        TimerTask minTimeTask = null;
        long minInterval = 0;
        for (Map.Entry<Long, TimerTask> entry : map.entrySet()) {
            if (!entry.getValue().getConfig().getActive()) {
                continue;
            }
            TimerTask timerTask = entry.getValue();
            Long interval = getInterval(timerTask);
            if (interval != null) {
                if (minTimeTask == null) {
                    minTimeTask = timerTask;
                    minInterval = interval;
                }
            } else {
                if (interval < minInterval) {
                    minTimeTask = timerTask;
                    minInterval = interval;
                }
            }
        }
        return minTimeTask;
    }

    private Long getInterval(TimerTask timerTask) {
        ExecuteConfig config = timerTask.getConfig();
        Date date = new Date();
        long currentTime = System.currentTimeMillis();
        switch (ExecuteTimeEnum.valueOf(config.getTimeType())) {
            case Time: {
                Date executionDate = DtUtils.getTodayTime(config.getTime());
                long executionTime = executionDate.getTime();
                Date lastExecutionDate = DtUtils.parseUnixTimeMs(timerTask.getLastExecutionTime());
                if (DateUtils.isSameDay(lastExecutionDate, date)) { //今日已执行
                    if (currentTime > executionTime) {
                        executionTime = DateUtils.addDays(executionDate, 1).getTime();
                    }
                }
                return executionTime - currentTime;
            }
            case Interval: {
                if (timerTask.getLastExecutionTime() == 0) {
                    return config.getInterval();
                } else {
                    return currentTime - (timerTask.getLastExecutionTime() + config.getInterval());
                }
            }
        }
        return null;
    }


    public void mainLoop() {
        while (true) {
            try {
                synchronized (map) {
                    if (map.size() == 0) {
                        map.wait();
                    }
                    TimerTask timerTask = getMin();
                    if (timerTask == null) {
                        map.wait();
                    }
                    long interval = getInterval(timerTask);
                    if (interval < 0) {
                        ExecuteConfig config = timerTask.getConfig();
                        scheduleLinkHandle.scheduleGroup(config.getGroupId(), "", config.getId());
                        timerTask.setLastExecutionTime(System.currentTimeMillis());
                    } else {
                        map.wait(interval);
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }


}