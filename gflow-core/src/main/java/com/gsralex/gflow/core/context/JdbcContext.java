package com.gsralex.gflow.core.context;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.config.GFlowConfig;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class JdbcContext {

    private GFlowConfig config;
    private JdbcUtils jdbcUtils;

    public JdbcContext(GFlowConfig config) {
        this.config = config;
    }


    private void initSql() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(config.getDbDriver());
        dataSource.setUrl(config.getDbUrl());
        dataSource.setUsername(config.getDbUsername());
        dataSource.setPassword(config.getDbPassword());
        this.jdbcUtils = new JdbcUtils(dataSource);
    }

    public JdbcUtils getJdbcUtils() {
        return this.jdbcUtils;
    }
}
