package com.gsralex.gflow.scheduler.sql;


import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/12
 */
public interface FlowJobDao {


    boolean saveJobGroup(GFlowJobGroup jobGroup);

    boolean updateJobGroup(GFlowJobGroup jobGroup);

    GFlowJobGroup getJobGroup(long id);

    int getJobGroupByExecute(long executeId, int date);

    boolean saveJob(GFlowJob job);

    boolean updateJob(GFlowJob job);

    GFlowJob getJob(long id);

    int batchSaveJob(List<GFlowJob> jobList);

    List<GFlowJob> getJobNeedRetryList(int[] statusArray, int retryCnt);


    List<GFlowJob> listJob(long jobGroupId);
}
