package com.gsralex.gflow.scheduler.timer;

import com.gsralex.gflow.core.domain.TimerConfigPo;
import com.gsralex.gflow.core.rpc.client.RpcClientFactory;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.ScheduleService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gsralex
 * @version 2018/8/21
 */
@Component
public class TimerProcess {
    private TimerHandler timerHandler = new TimerHandler();

    public void start() {
        Thread t = new Thread(timerHandler);
        t.start();
    }

    public void stop() {
        timerHandler.stop();
    }

    public void setTimer(TimerTask task) {
        timerHandler.setTimer(task);
    }

    public void removeTimer(long id) {
        timerHandler.removeTimer(id);
    }

    public static class TimerHandler implements Runnable {

        private static final Logger LOG = LoggerFactory.getLogger(TimerProcess.class);

        private Map<Long, TimerTask> map = new HashMap<>();
        private volatile boolean interrupt = false;

        private ReentrantLock lock = new ReentrantLock();
        private Condition timers = lock.newCondition();

        public void setTimer(TimerTask task) {
            try {
                lock.lock();
                map.put(task.getTimerConfig().getId(), task);
                timers.signal();
            } finally {
                lock.unlock();
            }
        }

        public void removeTimer(long id) {
            try {
                lock.lock();
                map.remove(id);
                timers.signal();
            } finally {
                lock.unlock();
            }
        }

        public TimerTask getMin(Date date) {
            TimerTask minTimeTask = null;
            long minInterval = 0;
            //O(n)
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
            TimerConfigPo config = timerTask.getTimerConfig();
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


        public void stop() {
            interrupt = true;
        }

        @Override
        public void run() {
            while (!interrupt) {
                synchronized (map) {
                    try {
                        if (map.size() == 0) {
                            timers.await(10, TimeUnit.SECONDS);
                        }
                        TimerTask timerTask = getMin(new Date());
                        //存在已经为size为0，依然删除的情况，会触发notify
                        if (timerTask != null) {
                            long interval = getInterval(timerTask, new Date());
                            if (interval < 0) {
                                TimerConfigPo config = timerTask.getTimerConfig();
                                doScheduler(config.getFlowGroupId(), config.getParameter(), config.getId());
                                LOG.info("timer executor job,timerid:{},flowgroupid:{}",
                                        config.getId(), config.getFlowGroupId());
                                timerTask.setLastExecutionTime(System.currentTimeMillis());
                            } else {
                                timers.await(interval, TimeUnit.MICROSECONDS);
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("TimerHandler.mainLoop", e);
                    }
                }
            }
        }

        private void doScheduler(Long groupId, String parameter, Long timerConfigId) {
            ScheduleService scheduleService = RpcClientFactory.create(ScheduleService.class,
                    SchedulerContext.getInstance().getSchedulerIpManager());
            scheduleService.scheduleGroup(groupId, parameter, timerConfigId);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        TimerProcess process = new TimerProcess();
        process.start();
        for (int i = 0; i < 10000; i++) {
            TimerConfigPo config1 = new TimerConfigPo();
            config1.setTime("22:56:40");
            config1.setActive(true);
            config1.setId(new Long(i));
            TimerTask timerTask1 = new TimerTask(config1);
            process.setTimer(timerTask1);
        }
        TimerConfigPo config2 = new TimerConfigPo();
        config2.setTime("22:38:51");
        config2.setActive(true);
        config2.setId(2L);
        TimerTask timerTask2 = new TimerTask(config2);
        process.setTimer(timerTask2);
    }
}