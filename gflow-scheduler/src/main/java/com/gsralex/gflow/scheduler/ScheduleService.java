package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.core.domain.result.JobResult;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public interface ScheduleService {

    JobResult submit(long actionId);

    JobResult submitGroup(long triggerGroupId);

    JobResult pauseJobGroup(long jobGroupId);

    JobResult stopJobGroup(long jobGroupId);
}
