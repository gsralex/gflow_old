package com.gsralex.gflow.scheduler.impl;

import com.gsralex.gflow.scheduler.ScheduleService;
import com.gsralex.gflow.scheduler.context.GFlowContext;
import com.gsralex.gflow.scheduler.domain.job.JobResult;


/**
 * @author gsralex
 * @date 2018/3/3
 */
public class ScheduleServiceImpl implements ScheduleService {


    @Override
    public JobResult submit(long actionId, String parameter) {
        return null;
    }

    @Override
    public JobResult submitGroup(long triggerGroupId, String parameter) {
        return null;
    }

    @Override
    public JobResult pauseJobGroup(long jobGroupId, String parameter) {
        return null;
    }

    @Override
    public JobResult stopJobGroup(long jobGroupId) {
        return null;
    }


    public static void main(String[] args) {
        GFlowContext context = new GFlowContext();
        context.init();
    }
}
