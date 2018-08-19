package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gflow.core.context.JdbcContext;
import com.gsralex.gflow.scheduler.sql.ExecuteDao;
import com.gsralex.gflow.core.domain.GFlowExecuteConfig;

import java.util.List;

public class ExecuteDaoImpl implements ExecuteDao {


    private JdbcContext jdbcContext;
    public ExecuteDaoImpl(JdbcContext jdbcContext){
        this.jdbcContext=jdbcContext;
    }

    @Override
    public List<GFlowExecuteConfig> listExecuteConfig() {
        String sql="select * from gflow_executeconfig";
        return jdbcContext.getJdbcUtils().queryForList(sql,null,GFlowExecuteConfig.class);
    }
}
