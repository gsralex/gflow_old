package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.core.domain.JobPo;
import com.gsralex.gflow.core.domain.JobGroupPo;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface JobDao {

    /**
     * 保存任务
     *
     * @param jobGroupPo
     * @return
     */
    boolean saveJobGroup(JobGroupPo jobGroupPo);

    /**
     * 更新任务
     *
     * @param jobGroupPo
     * @return
     */
    boolean updateJobGroup(JobGroupPo jobGroupPo);

    JobGroupPo getJobGroup(long id);

    boolean saveJob(JobPo jobPo);

    boolean updateJob(JobPo jobPo);

    boolean updateJobStatus(long id, int status);

    JobPo getJob(long id);

    boolean incrJobRetryCnt(long id);

    List<JobGroupPo> listJobGroupExecuting();

    List<JobPo> listJob(long jobGroupId);

    List<JobPo> listJobNeedRetry(int maxRetryCnt);

    List<TimerExecuteRecord> listJobGroupExec(List<Long> timerIdList);
}
