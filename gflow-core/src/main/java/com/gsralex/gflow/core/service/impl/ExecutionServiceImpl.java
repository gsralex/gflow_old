package com.gsralex.gflow.core.service.impl;

import com.gsralex.gflow.core.dao.ExecutionDao;
import com.gsralex.gflow.core.domain.GFlowExecution;
import com.gsralex.gflow.core.service.ExecutionService;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/3/16
 */
public class ExecutionServiceImpl implements ExecutionService {

    private ExecutionDao executionDao;

    @Override
    public List<GFlowExecution> listActiveExecution() {
        return executionDao.listActiveExecution();
    }
}
