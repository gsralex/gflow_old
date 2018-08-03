package com.gsralex.gflow.scheduler.context;

import com.gsralex.gflow.scheduler.util.PropertiesUtils;
import org.apache.log4j.Logger;

/**
 * @author gsralex
 * @date 2018/2/21
 */
public class GFlowContext {
    private static final Logger logger=Logger.getLogger(GFlowContext.class);

    private static final String CONFIG_FILEPATH = "gflow.properties";
    private GFlowConfig config;
    private JdbcContext jdbcContext;
    private ZkContext zkContext;


    public void init() {
        initConfig();
        jdbcContext=new JdbcContext(this.config);
        zkContext=new ZkContext(this.config);
    }

    private void initConfig() {
        String filePath = PropertiesUtils.class.getResource("/" + CONFIG_FILEPATH).getPath();
        try {
            config = PropertiesUtils.getConfig(filePath, GFlowConfig.class);
        } catch (Exception e) {
            logger.error("GflowContext.initConfig",e);
        }
    }

    public JdbcContext getJdbcContext(){
        return jdbcContext;
    }

    public ZkContext getZkContext() {
        return zkContext;
    }

    public GFlowConfig getConfig() {
        return config;
    }
}
