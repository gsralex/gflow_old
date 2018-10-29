package com.gsralex.gflow.scheduler.sql;


import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/12
 */
public interface FlowJobDao {

    /**
     * 保存任务
     *
     * @param jobGroup
     * @return
     */
    boolean saveJobGroup(GFlowJobGroup jobGroup);

    /**
     * 更新任务
     *
     * @param jobGroup
     * @return
     */
    boolean updateJobGroup(GFlowJobGroup jobGroup);

    GFlowJobGroup getJobGroup(long id);

    boolean saveJob(GFlowJob job);

    boolean updateJob(GFlowJob job);

    boolean updateJobStatus(long id, int status);

    GFlowJob getJob(long id);

    boolean incrJobRetryCnt(long id);

    List<GFlowJobGroup> listJobGroupExecuting();

    List<GFlowJob> listJob(long jobGroupId);

    List<GFlowJob> listJobNeedRetry(int maxRetryCnt);
}
