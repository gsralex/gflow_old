package com.gsralex.gflow.scheduler.retry;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.enums.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.schedule.ScheduleResult;
import com.gsralex.gflow.scheduler.service.FlowJobService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gsralex
 * @version 2018/8/21
 */
public class RetryProcessor {

    private Map<Long, RetryTask> taskMap = new ConcurrentHashMap<>();
    private GFlowContext context;
    private GFlowConfig config;
    private FlowJobService flowJobService;

    public RetryProcessor(GFlowContext context) {
        this.context = context;
        this.config = context.getConfig();
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLoop();
            }
        }).start();
    }

    public void put(RetryTask task) {
        synchronized (taskMap) {
            long key = task.getRetryJobId();
            if (!taskMap.containsKey(key)) {
                taskMap.put(key, task);
                taskMap.notify();
            }
        }
    }

    public void put(ScheduleResult result) {
        RetryTask task = new RetryTask();
        task.setGroupId(result.getGroupId());
        task.setJobGroupId(result.getJobGroupId());
        task.setActionId(result.getActionId());
        task.setIndex(result.getIndex());
        task.setRetryTime(System.currentTimeMillis());
        task.setRetryCnt(0);

        switch (result.getStatus()) {
            //任务状态在schedule，不需要设置timeout时间
            case SendErr:
            case ExecuteErr: {
                task.setTimeoutTime(0);
                break;
            }
            case SendOk: {
                task.setTimeoutTime(config.getJobTimeout());
                break;
            }
        }
        task.setRetryTime(System.currentTimeMillis());
        this.put(task);
    }

    public void update(long jobId, boolean ok) {
        synchronized (taskMap) {
            if (taskMap.containsKey(jobId)) {
                RetryTask task = taskMap.get(jobId);
                if (ok) {
                    taskMap.remove(jobId);
                } else {
                    //更新重试时间
                    task.setTimeoutTime(0);
                    task.setRetryTime(System.currentTimeMillis());
                }
                task.notify();
            }
        }
    }


    private RetryTask getMin() {
        RetryTask minTask = null;
        long minInterval = 0;
        for (Map.Entry<Long, RetryTask> entry : taskMap.entrySet()) {
            RetryTask task = entry.getValue();
            Long interval = getInterval(task);
            if (minTask == null) {
                minTask = task;
                minInterval = interval;
            } else {
                if (interval < minInterval) {
                    minTask = task;
                    minInterval = interval;
                }
            }
        }
        return minTask;
    }

    private Long getInterval(RetryTask task) {
        long currentTime = System.currentTimeMillis();
        //重试时间
        long retryWaitTime = Math.round(Math.pow(2, (task.getRetryCnt() - 1)) * config.getRetryInterval()); //2^retrycnt*interval
        //下次执行时间=放入时间+任务超时时间+重试等待时间
        long nextExecutionTime = task.getRetryTime() + task.getTimeoutTime() + retryWaitTime;
        return nextExecutionTime - currentTime;
    }


    public void mainLoop() {
        while (true) {
            try {
                synchronized (taskMap) {
                    if (taskMap.size() == 0) {
                        taskMap.wait();
                    }
                    RetryTask task = getMin();
                    if (task == null) {
                        taskMap.wait();
                    }
                    if (task.getRetryCnt() < config.getRetryCnt()) {
                        //调度执行
                        task.setRetryCnt(task.getRetryCnt() + 1);
                        task.setRetryTime(System.currentTimeMillis());
                        task.setTimeoutTime(config.getJobTimeout());
                        //TODO:更新持久化重试次数
                    } else {
                        taskMap.remove(task.getRetryJobId());
                        //TODO:更新至删除
                        flowJobService.updateJobGroup(task.getJobGroupId(), JobGroupStatusEnum.STOP);
                        continue;
                    }
                    long interval = getInterval(task);
                    if (interval < 0) {
                        execute(task);
                    } else {
                        taskMap.wait(interval);
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }

    private void execute(RetryTask task) {

    }
}
