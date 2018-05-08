package com.gsralex.gflow.scheduler.context;


import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.util.PropertiesUtils;


import javax.sql.DataSource;

/**
 * @author gsralex
 * @date 2018/2/21
 */
public class GFlowContext {
    private static final String CONFIG_FILEPATH = "gflow.properties";

    private GFlowConfig config;
    private DataSource dataSource;
    private JdbcUtils jdbcUtils;

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
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(config.dbDriver);
//        dataSource.setUrl(config.dbUrl);
//        dataSource.setUsername(config.dbUserName);
//        dataSource.setPassword(config.dbUserPass);
//        this.dataSource = dataSource;

    }
}
