package com.gsralex.gflow.web.configuration;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.web.WebContext;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Configuration
@ComponentScan(basePackages = "com.gsralex.gflow.web")
public class SpringConfiguration implements WebMvcConfigurer {
    private static final String CONFIG_FILEPATH = "/gflow.properties";
    public SpringConfiguration() throws IOException {
        WebContext.getInstance().init();
    }

    @Bean
    public JdbcUtils jdbcUtils() {
        WebConfig config = WebContext.getInstance().getConfig();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(config.getDbDriver());
        dataSource.setUrl(config.getDbUrl());
        dataSource.setUsername(config.getDbUsername());
        dataSource.setPassword(config.getDbPassword());
        return new JdbcUtils(dataSource);
    }
}
