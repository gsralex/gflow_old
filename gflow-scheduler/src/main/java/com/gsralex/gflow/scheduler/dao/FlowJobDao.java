package com.gsralex.gflow.scheduler.dao;


import com.gsralex.gflow.scheduler.domain.persistent.GFlowJob;
import com.gsralex.gflow.scheduler.domain.persistent.GFlowJobGroup;

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
