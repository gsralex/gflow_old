package com.gsralex.gflow.scheduler.configuration;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Configuration
@ComponentScan(basePackages = {"com.gsralex.gflow.scheduler"})
public class SpringConfiguration {
    @Bean
    public JdbcUtils jdbcUtils() {
        SchedulerContext context = SchedulerContext.getInstance();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(context.getConfig().getDbDriver());
        dataSource.setUrl(context.getConfig().getDbUrl());
        dataSource.setUsername(context.getConfig().getDbUsername());
        dataSource.setPassword(context.getConfig().getDbPassword());
        return new JdbcUtils(dataSource);

    }
}
