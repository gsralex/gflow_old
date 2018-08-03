package com.gsralex.gflow.scheduler.context;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import org.apache.commons.dbcp.BasicDataSource;


public class JdbcContext {

    private GFlowConfig config;
    private JdbcUtils jdbcUtils;

    public JdbcContext(GFlowConfig config){
        this.config=config;
        initGData();
    }

    private void initGData() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(config.getDbDriver());
        dataSource.setUrl(config.getDbUrl());
        dataSource.setUsername(config.getDbUsername());
        dataSource.setPassword(config.getDbPassword());
        this.jdbcUtils=new JdbcUtils(dataSource);
    }

    public JdbcUtils getJdbcUtils() {
        return jdbcUtils;
    }
}
