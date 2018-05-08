package com.gsralex.gflow.scheduler.impl;

import com.gsralex.gflow.core.dao.ActionDao;
import com.gsralex.gflow.core.domain.result.JobResult;
import com.gsralex.gflow.scheduler.ScheduleContext;
import com.gsralex.gflow.scheduler.ScheduleService;
import com.gsralex.gflow.scheduler.rpc.JobDesc;

import java.util.List;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public class ScheduleServiceImpl implements ScheduleService {


    private ScheduleContext context;
    private ActionDao actionDao;

    private ScheduleServiceImpl(ScheduleContext context) {
        this.context = context;
        actionDao = new ActionDao(this.context.getFlowContext());

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
