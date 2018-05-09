package com.gsralex.gflow.scheduler.impl;

import com.gsralex.gflow.core.domain.result.JobResult;
import com.gsralex.gflow.scheduler.ScheduleContext;
import com.gsralex.gflow.scheduler.ScheduleService;
/**
 * @author gsralex
 * @date 2018/3/3
 */
public class ScheduleServiceImpl implements ScheduleService {


    private ScheduleContext context;


    private ScheduleServiceImpl(ScheduleContext context) {
        this.context = context;

    }

    @Override
    public JobResult submit(long actionId) {
        return null;
    }

    @Override
    public JobResult submitGroup(long triggerGroupId) {
        return null;
    }

    @Override
    public JobResult pauseJobGroup(long jobGroupId) {
        return null;
    }

    @Override
    public JobResult stopJobGroup(long jobGroupId) {
        return null;
    }
}
