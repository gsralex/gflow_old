package com.gsralex.gflow.scheduler.context;


import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.util.PropertiesUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * @author gsralex
 * @date 2018/2/21
 */
public class GFlowContext {

    private static final Logger LOGGER = Logger.getLogger(GFlowContext.class);

    private static final String CONFIG_FILEPATH = "gflow.properties";

    private GFlowConfig config;
    private JdbcUtils jdbcUtils;
    private BeanMap beanMap;

    private GFlowConfig getConfig() {
        return config;
    }

    public JdbcUtils getJdbcUtils() {
        return jdbcUtils;
    }


    public void init() {
        initConfig();
        initDataSource();
        initBeanMap();
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
        dataSource.setDriverClassName(config.dbDriver);
        dataSource.setUrl(config.dbUrl);
        dataSource.setUsername(config.dbUserName);
        dataSource.setPassword(config.dbUserPass);
        jdbcUtils = new JdbcUtils(dataSource);
    }

    private void initBeanMap() {
        beanMap = new BeanMap();
    }

    public BeanMap getBeanMap() {
        return this.beanMap;
    }
}
