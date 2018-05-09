package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.domain.GFlowExecution;
import com.gsralex.gflow.scheduler.context.GFlowContext;
import com.gsralex.gflow.scheduler.dao.JobExecDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/8
 */
public class JobExecDaoImpl implements JobExecDao {

    private JdbcUtils jdbcUtils;

    public JobExecDaoImpl(GFlowContext context) {
        this.jdbcUtils = context.getJdbcUtils();
    }

    @Override
    public List<GFlowExecution> getExecutionList() {
        String sql = "select * from gflow_execution";
        return jdbcUtils.queryForList(sql, null, GFlowExecution.class);
    }
}
