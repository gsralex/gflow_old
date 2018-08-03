package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.domain.job.JobResult;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public interface ScheduleProcess {

    JobResult start(long actionId, String parameter);

    JobResult startGroup(long triggerGroupId, String parameter);

    JobResult pauseJobGroup(long jobGroupId);

    JobResult stopJobGroup(long jobGroupId);

    JobResult actionAck(long jobGroupId, long actionId, boolean ok);
}
