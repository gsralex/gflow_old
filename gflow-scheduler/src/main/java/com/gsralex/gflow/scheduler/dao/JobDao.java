package com.gsralex.gflow.scheduler.dao;


import com.gsralex.gflow.scheduler.domain.Job;
import com.gsralex.gflow.scheduler.domain.JobGroup;
import com.gsralex.gflow.scheduler.model.JobGroupExec;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/12
 */
public interface JobDao {

    /**
     * 保存任务
     *
     * @param jobGroup
     * @return
     */
    boolean saveJobGroup(JobGroup jobGroup);

    /**
     * 更新任务
     *
     * @param jobGroup
     * @return
     */
    boolean updateJobGroup(JobGroup jobGroup);

    JobGroup getJobGroup(long id);

    boolean saveJob(Job job);

    boolean updateJob(Job job);

    boolean updateJobStatus(long id, int status);

    Job getJob(long id);

    boolean incrJobRetryCnt(long id);

    List<JobGroup> listJobGroupExecuting();

    List<Job> listJob(long jobGroupId);

    List<Job> listJobNeedRetry(int maxRetryCnt);

    List<JobGroupExec> listJobGroupExec(List<Long> timerIdList);
}
