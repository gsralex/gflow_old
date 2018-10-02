package com.gsralex.gflow.core.context;


import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.gflow.core.zk.ZkContext;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gsralex
 * @date 2018/2/21
 */
public class GFlowContext {

    private static GFlowContext currentContext = new GFlowContext();

    private GFlowContext() {
    }

    public static GFlowContext getContext() {
        return currentContext;
    }

    private static final Logger logger = Logger.getLogger(GFlowContext.class);
    private static String CONFIG_FILEPATH = "/gflow.properties";
    private GFlowConfig config;
    private ZkContext zkContext;
    private JdbcContext jdbcContext;

    public void initConfig() {
        InputStream is = PropertiesUtils.class.getResourceAsStream(CONFIG_FILEPATH);
        try {
            config = PropertiesUtils.getConfig(is, GFlowConfig.class);
        } catch (Exception e) {
            //TODO:加入异常
        }
    }

    public void initZk() {
        zkContext = new ZkContext(config);
    }

    public void initJdbc() {
        jdbcContext = new JdbcContext(config);
    }


    public ZkContext getZkContext() {
        return zkContext;
    }


    public void setConfig(GFlowConfig config) {
        this.config = config;
    }

    public GFlowConfig getConfig() {
        return config;
    }


}
