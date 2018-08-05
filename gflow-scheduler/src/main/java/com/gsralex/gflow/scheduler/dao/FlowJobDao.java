package com.gsralex.gflow.scheduler.dao;


import com.gsralex.gflow.scheduler.domain.flow.GFlowJob;
import com.gsralex.gflow.scheduler.domain.flow.GFlowJobGroup;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/12
 */
public interface FlowJobDao {


    boolean saveJobGroup(GFlowJobGroup jobGroup);

    boolean updateJobGroup(GFlowJobGroup jobGroup);

    GFlowJobGroup getJobGroup(long id);

    int getJobGroupByExecute(long triggerGroupId, int date);

    boolean saveJob(GFlowJob job);

    int batchSaveJob(List<GFlowJob> jobList);

}
