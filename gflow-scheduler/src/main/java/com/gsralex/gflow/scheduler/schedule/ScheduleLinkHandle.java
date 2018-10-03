package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.retry.RetryProcessor;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调度链路
 *
 * @author gsralex
 * @version 2018/8/22
 */
@Service
public class ScheduleLinkHandle {

    @Autowired
    private ScheduleActualHanle scheduleActualHanle;
    @Autowired
    private FlowJobDao flowJobDao;


    public void scheduleGroup(long groupId, Parameter parameter, long executeConfigId) {
        List<ScheduleResult> r = scheduleActualHanle.scheduleGroup(groupId, parameter, executeConfigId);
        setupRetryTask(r);
    }

    public void pauseGroup(long jobGroupId) {
        scheduleActualHanle.pauseGroup(jobGroupId);
    }

    public void continueGroup(long jobGroupId) {
        List<ScheduleResult> r = scheduleActualHanle.continueGroup(jobGroupId);
        setupRetryTask(r);
    }


    public void scheduleAction(long actionId, Parameter parameter) {
        scheduleActualHanle.scheduleAction(actionId, parameter);
    }

    public void ackAction(long jobId, boolean ok) {
        RetryProcessor retryProcessor = SchedulerContext.getContext().getRetryProcessor();
        if (ok) {
            GFlowJob job = flowJobDao.getJob(jobId);
            if (job != null) {
                List<ScheduleResult> r = scheduleActualHanle.actionAck(jobId, ok);
                setupRetryTask(r);
                if (job.getRetryJobId() != 0) {
                    jobId = job.getRetryJobId();
                }


                retryProcessor.update(jobId, true);
                //TODO:持久化到sql
            }
        } else {
            GFlowJob job = flowJobDao.getJob(jobId);
            if (job != null) {
                if (job.getRetryJobId() != 0) {
                    jobId = job.getRetryJobId();
                }
                retryProcessor.update(jobId, false);
                //TODO:持久化到sql
            }
        }
    }

    private void setupRetryTask(List<ScheduleResult> r) {
        RetryProcessor retryProcessor = SchedulerContext.getContext().getRetryProcessor();
        for (ScheduleResult result : r) {
            retryProcessor.put(result);
        }
    }
}
