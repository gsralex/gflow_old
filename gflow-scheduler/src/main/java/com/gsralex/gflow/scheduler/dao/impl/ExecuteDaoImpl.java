package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gflow.scheduler.context.JdbcContext;
import com.gsralex.gflow.scheduler.dao.ExecuteDao;
import com.gsralex.gflow.scheduler.domain.flow.GFlowExecuteConfig;

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
