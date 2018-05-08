package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.domain.GFlowExecution;
import com.gsralex.gflow.scheduler.dao.JobExecDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/8
 */
public class JobExecDaoImpl implements JobExecDao {

    private JdbcUtils jdbcUtils;

    public JobExecDaoImpl(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public List<GFlowExecution> getExecutionList() {
        return null;
    }
}
