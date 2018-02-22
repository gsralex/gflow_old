package com.gsralex.gflow.core.dao;

import com.gsralex.gflow.core.domain.GFlowExecution;

import java.util.List;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public interface ExecutionDao {

    List<GFlowExecution> listActiveExecution();
}
