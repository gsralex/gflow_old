package com.gsralex.gflow.core.context;


import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.util.PropertiesUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.URL;

/**
 * @author gsralex
 * @date 2018/2/21
 */
public class GFlowContext {
    private static final Logger logger = Logger.getLogger(GFlowContext.class);

    private static final String CONFIG_FILEPATH = "/gflow.properties";
    private GFlowConfig config;
    private JdbcContext jdbcContext;
    private ZkContext zkContext;


    public void init() {
        initConfig();
        jdbcContext = new JdbcContext(this.config);
        zkContext = new ZkContext(this.config);
    }

    private void initConfig() {
        InputStream is = PropertiesUtils.class.getResourceAsStream(CONFIG_FILEPATH);
        try {
            config = PropertiesUtils.getConfig(is, GFlowConfig.class);
        } catch (Exception e) {
            logger.error("GflowContext.initConfig", e);
        }
    }

    public JdbcContext getJdbcContext() {
        return jdbcContext;
    }

    public ZkContext getZkContext() {
        return zkContext;
    }

    public GFlowConfig getConfig() {
        return config;
    }
}
