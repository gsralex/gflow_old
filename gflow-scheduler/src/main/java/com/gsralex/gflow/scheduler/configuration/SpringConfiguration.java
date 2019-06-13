package com.gsralex.gflow.scheduler.configuration;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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
        SchedulerConfig config = context.getConfig();
        HikariConfig dbConfig = new HikariConfig();
        dbConfig.setDriverClassName(config.getDbDriver());
        dbConfig.setJdbcUrl(config.getDbUrl());
        dbConfig.setUsername(config.getDbUsername());
        dbConfig.setPassword(config.getDbPassword());

        HikariDataSource dataSource = new HikariDataSource(dbConfig);
        return new JdbcUtils(dataSource);

    }
}
