package com.gsralex.gflow.webmanager.configuration;

import com.gsralex.gflow.core.config.GFlowConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author gsralex
 * @version 2018/9/5
 */
@Configuration
public class DataSourceConfig {

    private static final String GFLOWPATH = "/gflow.properties";

    @Bean(name = "dataSource",destroyMethod = "close")
    public DataSource dataSource() {
        GFlowConfig config = GFlowConfig.getConfig(GFLOWPATH);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(config.getDbDriver());
        dataSource.setUrl(config.getDbUrl());
        dataSource.setUsername(config.getDbUsername());
        dataSource.setPassword(config.getDbPassword());
        return dataSource;
    }

}
