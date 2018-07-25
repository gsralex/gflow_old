package com.gsralex.gflow.scheduler.context;


import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.util.PropertiesUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @date 2018/2/21
 */
public class GFlowContext {

    private static final Logger LOGGER = Logger.getLogger(GFlowContext.class);

    private static final String CONFIG_FILEPATH = "gflow.properties";

    private GFlowConfig config;
    private JdbcUtils jdbcUtils;
    private static Map<String, Object> beanMap = new HashMap<>();


    private GFlowConfig getConfig() {
        return config;
    }

    public JdbcUtils getJdbcUtils() {
        return jdbcUtils;
    }


    public void init() {
        initConfig();
        initDataSource();
    }

    private void initConfig() {
        String filePath = PropertiesUtils.class.getResource("/" + CONFIG_FILEPATH).getPath();
        try {
            config = PropertiesUtils.getConfig(filePath, GFlowConfig.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void initDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(config.getDbDriver());
        dataSource.setUrl(config.getDbUrl());
        dataSource.setUsername(config.getDbUsername());
        dataSource.setPassword(config.getDbPassword());
        jdbcUtils = new JdbcUtils(dataSource);
    }


    public <T> T getBean(Class<T> type) {
        return (T) beanMap.get(type.getName());
    }

    public void setBean(Class type, Object instance) {
        beanMap.put(type.getName(), instance);
    }
}
