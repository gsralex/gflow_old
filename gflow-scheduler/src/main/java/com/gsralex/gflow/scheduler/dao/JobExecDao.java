package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.core.domain.GFlowExecution;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/8
 */
public interface JobExecDao {

    List<GFlowExecution> getExecutionList();

}
