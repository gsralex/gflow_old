package com.gsralex.gflow.scheduler.dao;


import com.gsralex.gflow.scheduler.domain.flow.GFlowJob;
import com.gsralex.gflow.scheduler.domain.flow.GFlowJobGroup;

/**
 * @author gsralex
 * @version 2018/5/12
 */
public interface FlowJobDao {


    boolean saveJobGroup(GFlowJobGroup jobGroup);

    boolean updateJobGroup(GFlowJobGroup jobGroup);

    GFlowJobGroup getJobGroup(long id);

    GFlowJobGroup getJobGroupByExecute(long triggerGroupId, String date);

    boolean saveJob(GFlowJob job);
}
