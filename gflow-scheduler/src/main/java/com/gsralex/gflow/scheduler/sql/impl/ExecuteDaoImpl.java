package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.sql.ExecuteDao;
import com.gsralex.gflow.scheduler.domain.TimerConfig;

import java.util.List;

public class ExecuteDaoImpl implements ExecuteDao {


    private JdbcUtils jdbcUtils;

    public ExecuteDaoImpl(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public List<TimerConfig> listExecuteConfig() {
        String sql = "select * from gflow_executeconfig";
        return jdbcUtils.queryForList(sql, null, TimerConfig.class);
    }
}
