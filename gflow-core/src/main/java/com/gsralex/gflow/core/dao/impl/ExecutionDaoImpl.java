package com.gsralex.gflow.core.dao.impl;

import com.gsralex.gflow.core.constant.DbTableConstants;
import com.gsralex.gflow.core.dao.ExecutionDao;
import com.gsralex.gflow.core.domain.GFlowExecution;

import java.util.List;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public class ExecutionDaoImpl implements ExecutionDao {



    @Override
    public List<GFlowExecution> listActiveExecution() {
        String sql = String.format("select * from %s where active=1", DbTableConstants.GFLOW_EXECUTION);
        return null;
    }
}
