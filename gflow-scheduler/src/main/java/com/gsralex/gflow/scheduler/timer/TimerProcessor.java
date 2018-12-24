package com.gsralex.gflow.scheduler.timer;

import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.domain.TimerConfig;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/8/21
 */
public class TimerProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimerProcessor.class);

    private Map<Long, TimerTask> map = new HashMap<>();
    private ScheduleLinkHandle scheduleLinkHandle;


    private static final TimerProcessor current = new TimerProcessor();

    private TimerProcessor() {

    }

    public void setContext(SchedulerContext context) {
        scheduleLinkHandle = new ScheduleLinkHandle(context);
    }

    public static TimerProcessor getInstance() {
        return current;
    }


    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLoop();
            }
        }).start();
    }

    public void setTimer(TimerTask task) {
        synchronized (map) {
            map.put(task.getTimerConfig().getId(), task);
            map.notify();
        }
    }

    public void removeTimer(long id) {
        synchronized (map) {
            map.remove(id);
            map.notify();
        }
    }

    public TimerTask getMin(Date date) {
        TimerTask minTimeTask = null;
        long minInterval = 0;
        for (Map.Entry<Long, TimerTask> entry : map.entrySet()) {
            if (!entry.getValue().getTimerConfig().getActive()) {
                continue;
            }
            TimerTask timerTask = entry.getValue();
            Long interval = getInterval(timerTask, date);
            if (minTimeTask == null) {
                minTimeTask = timerTask;
                minInterval = interval;
            } else {
                if (interval < minInterval) {
                    minTimeTask = timerTask;
                    minInterval = interval;
                }
            }
        }
        return minTimeTask;
    }

    public long getInterval(TimerTask timerTask, Date date) {
        TimerConfig config = timerTask.getTimerConfig();
        long currentTime = date.getTime();
        Date execDate = getTodayDate(config.getTime(), date);
        long execTime = execDate.getTime();
        long interval = execTime - currentTime;
        if (interval < 0) {
            Date lastExecDate = DtUtils.parseUnixTimeMs(timerTask.getLastExecutionTime());
            if (DateUtils.isSameDay(lastExecDate, date)) {
                execTime = DateUtils.addDays(execDate, 1).getTime();
                interval = execTime - currentTime;
            }
        }
        return interval;
    }


    public Date getTodayDate(String time, Date date) {
        String[] timeArr = time.split(":");
        int hh = 0, mm = 0, ss = 0;
        if (timeArr.length > 0) {
            hh = Integer.parseInt(timeArr[0]);
        }
        if (timeArr.length > 1) {
            mm = Integer.parseInt(timeArr[1]);
        }
        if (timeArr.length > 2) {
            ss = Integer.parseInt(timeArr[2]);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hh);
        calendar.set(Calendar.MINUTE, mm);
        calendar.set(Calendar.SECOND, ss);
        return calendar.getTime();
    }


    public void mainLoop() {
        while (true) {
            try {
                synchronized (map) {
                    if (map.size() == 0) {
                        map.wait();
                    }
                    TimerTask timerTask = getMin(new Date());
                    if (timerTask == null) {
                        map.wait();
                    }
                    long interval = getInterval(timerTask, new Date());
                    if (interval < 0) {
                        TimerConfig config = timerTask.getTimerConfig();
                        scheduleLinkHandle.scheduleGroup(config.getFlowGroupId(), config.getParameter(), config.getId());
                        timerTask.setLastExecutionTime(System.currentTimeMillis());
                    } else {
                        map.wait(interval);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("TimerProcessor.mainLoop", e);
            }
        }
    }

}