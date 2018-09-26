package com.gsralex.gflow.scheduler.spring;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.context.GFlowContext;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author gsralex
 * @version 2018/9/21
 */
@Configuration
@ComponentScan(basePackages = {"com.gsralex.gflow.scheduler"})
public class SpringConfiguration {

    @Bean(name = "jdbcUtils")
    public JdbcUtils getJdbcUtils(DataSource dataSource) {
        return new JdbcUtils(dataSource);
    }

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource getDataSource() {
        GFlowContext context = GFlowContext.getContext();
        GFlowConfig config = context.getConfig();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(config.getDbDriver());
        dataSource.setUrl(config.getDbUrl());
        dataSource.setUsername(config.getDbUsername());
        dataSource.setPassword(config.getDbPassword());
        return dataSource;
    }

}

