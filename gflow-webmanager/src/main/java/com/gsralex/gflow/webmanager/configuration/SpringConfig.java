package com.gsralex.gflow.webmanager.configuration;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.sql.DataSource;

/**
 * @author gsralex
 * @version 2018/9/5
 */
@Configuration
@ComponentScan(basePackages = "com.gsralex.gflow.webmanager")
@Import({DataSourceConfig.class})
public class SpringConfig extends WebMvcConfigurationSupport {

    @Bean
    public JdbcUtils jdbcUtils(DataSource dataSource) {
        JdbcUtils jdbcUtils = new JdbcUtils(dataSource);
        return jdbcUtils;
    }
}
