package com.gsralex.gflow.scheduler.service;

import com.gsralex.gflow.core.domain.JobGroupPo;
import com.gsralex.gflow.core.domain.JobPo;
import com.gsralex.gflow.scheduler.schedule.ActionResult;
import com.gsralex.gflow.scheduler.schedule.FlowResult;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface BizSchedulerService {

    FlowResult scheduleGroup(long triggerGroupId,
                             String parameter,
                             long timerConfigId,
                             boolean retry);

    void pauseGroup(long jobGroupId);

    void stopGroup(long jobGroupId);

    FlowResult continueGroup(long jobGroupId, boolean retry);

    ActionResult scheduleAction(long actionId, String parameter, boolean retry);

    boolean ackAction(long jobId, boolean jobOk, String msg, boolean retry);

    boolean ackMaster(long jobId, boolean jobOk, String msg, boolean retry);


    JobPo getJob(long id);

    JobGroupPo getJobGroup(long id);
}
