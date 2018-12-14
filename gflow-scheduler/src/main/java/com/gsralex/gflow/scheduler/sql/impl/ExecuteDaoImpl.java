package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.sql.ExecuteDao;
import com.gsralex.gflow.core.domain.ExecuteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

public class ExecuteDaoImpl implements ExecuteDao {


    private JdbcUtils jdbcUtils;

    public ExecuteDaoImpl(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public List<ExecuteConfig> listExecuteConfig() {
        String sql = "select * from gflow_executeconfig";
        return jdbcUtils.queryForList(sql, null, ExecuteConfig.class);
    }
}
