package com.gsralex.gflow.core.service;

import com.gsralex.gflow.core.domain.GFlowExecution;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/3/16
 */
public interface ExecutionService {

    List<GFlowExecution> listActiveExecution();
}
