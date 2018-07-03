package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.domain.job.JobResult;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public interface ScheduleService {

    JobResult submit(long actionId, String parameter);

    JobResult submitGroup(long triggerGroupId, String parameter);

    JobResult pauseJobGroup(long jobGroupId, String parameter);

    JobResult stopJobGroup(long jobGroupId);

    JobResult actionAck(long jobGroupId, long actionId, boolean ok);
}
