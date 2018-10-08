package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.enums.JobStatusEnum;
import com.gsralex.gflow.core.util.DtUtils;
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
        FlowResult r = scheduleActualHanle.scheduleGroup(groupId, parameter, executeConfigId);
        setupRetryTask(r);
    }

    public void pauseGroup(long jobGroupId) {
        scheduleActualHanle.pauseGroup(jobGroupId);
    }

    public void continueGroup(long jobGroupId) {
        FlowResult r = scheduleActualHanle.continueGroup(jobGroupId);
        setupRetryTask(r);
    }


    public void scheduleAction(long actionId, Parameter parameter) {
        scheduleActualHanle.scheduleAction(actionId, parameter);
    }

    public void ackAction(long jobId, boolean ok) {
        FlowResult r = scheduleActualHanle.actionAck(jobId, ok);
        GFlowJob job = flowJobDao.getJob(jobId);
        if (job != null) {
            setupRetryTask(r);
            if (job.getRetryJobId() != 0) {
                jobId = job.getRetryJobId();
            }
            updateRetryTask(jobId, ok);
        }
    }

    private void setupRetryTask(FlowResult r) {
        GFlowContext context = GFlowContext.getContext();
        if (context.getConfig().getRetryActive() != null
                && context.getConfig().getRetryActive()) {
            RetryProcessor retryProcessor = SchedulerContext.getContext().getRetryProcessor();
            for (ScheduleResult result : r.getNextResults()) {
                retryProcessor.put(result);
            }
        }
    }

    private void updateRetryTask(long jobId, boolean ok) {
        GFlowContext context = GFlowContext.getContext();
        if (context.getConfig().getRetryActive() != null
                && context.getConfig().getRetryActive()) {
            RetryProcessor retryProcessor = SchedulerContext.getContext().getRetryProcessor();
            retryProcessor.update(jobId, ok);
        }

    }
}
