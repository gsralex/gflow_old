package com.gsralex.gflow.scheduler.retry;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.scheduler.schedule.ActionDesc;
import com.gsralex.gflow.scheduler.schedule.ActionResult;
import com.gsralex.gflow.scheduler.schedule.ScheduleActualHanle;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/8/21
 */
@Component
public class RetryProcessor {


    //key->taskId,value->task，重试任务
    private Map<Long, RetryTask> retryTaskMap = new HashMap<>();
    private GFlowConfig config = GFlowContext.getContext().getConfig();

    @Autowired
    private ScheduleActualHanle actualHanle;
    @Autowired
    private FlowJobDao flowJobDao;

    public void start() {
        recover();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLoop();
            }
        }).start();
    }

    private void recover() {
        List<GFlowJob> retryJobList = flowJobDao.listJobNeedRetry(config.getRetryCnt());
        for (GFlowJob job : retryJobList) {
            RetryTask retryTask = new RetryTask();
            retryTask.setJobId(job.getId());
            retryTask.setRetryTime(System.currentTimeMillis());
            retryTask.setRetryCnt(job.getRetryCnt());
            ActionDesc desc = new ActionDesc();
            desc.setTriggerGroupId(job.getTriggerGroupId());
            desc.setJobGroupId(job.getJobGroupId());
            desc.setIndex(job.getIndex());
            desc.setActionId(job.getActionId());
            desc.setParameter(job.getParameter());
            desc.setRetryJobId(job.getId()); //设置重试任务id
            retryTask.setActionDesc(desc);
            put(retryTask);
        }
    }

    public void put(RetryTask task) {
        synchronized (retryTaskMap) {
            long key = task.getJobId();
            if (!retryTaskMap.containsKey(key)) {
                retryTaskMap.put(key, task);
                retryTaskMap.notify();
            }
        }
    }

    public void mark(long jobId, long retryJobId, boolean ok) {
        synchronized (retryTaskMap) {
            long id;
            if (retryJobId != 0) {
                id = retryJobId;
            } else {
                id = jobId;
            }
            if (retryTaskMap.containsKey(id)) {
                RetryTask task = retryTaskMap.get(id);
                if (ok) {
                    retryTaskMap.remove(id);
                } else {
                    //更新重试时间
                    task.setTimeoutTime(0);
                    task.setRetryTime(System.currentTimeMillis());
                }
                retryTaskMap.notify();
            }
        }
    }

    private RetryTask getMin() {
        RetryTask minTask = null;
        long minInterval = 0;
        for (Map.Entry<Long, RetryTask> entry : retryTaskMap.entrySet()) {
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

    public Long getInterval(RetryTask task) {
        long currentTime = System.currentTimeMillis();
        //重试时间 2^retrycnt*retryintervalmills
        //1:2^0*10=10s
        //2:2^1*10=20s
        //3:2^2*10=40s
        //4:2^3*10=80s
        //5:2^4*10=160s
        long retryWaitTime = Math.round(Math.pow(2, task.getRetryCnt()) * config.getRetryIntervalMills()); //2^retrycnt*interval
        //下次执行时间=放入时间+任务超时时间+重试等待时间
        long nextExecutionTime = task.getRetryTime() + task.getTimeoutTime() + retryWaitTime;
        return nextExecutionTime - currentTime;
    }


    private void mainLoop() {
        while (true) {
            try {
                synchronized (retryTaskMap) {
                    if (retryTaskMap.size() == 0) {
                        retryTaskMap.wait();
                    }
                    RetryTask task = getMin();
                    if (task == null) {
                        retryTaskMap.wait();
                    }
                    if (task.getRetryCnt() >= config.getRetryCnt()) {
                        retryTaskMap.remove(task.getJobId());
                        continue;
                    }

                    long interval = getInterval(task);
                    if (interval < 0) {
                        //调度执行
                        task.setRetryCnt(task.getRetryCnt() + 1);
                        task.setRetryTime(System.currentTimeMillis());
                        //加入重试次数
                        flowJobDao.incrJobRetryCnt(task.getJobId());
                        ActionResult result = actualHanle.scheduleAction(task.getActionDesc(), false);
                    } else {
                        retryTaskMap.wait(interval);
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
