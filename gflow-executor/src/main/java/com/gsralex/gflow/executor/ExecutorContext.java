package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.client.RpcClientManager;
import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.gflow.executor.config.ExecutorConfig;
import com.gsralex.gflow.executor.spring.SpringContextHolder;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class ExecutorContext {

    private static final String CONFIG_FILEPATH = "/gflow.properties";

    private static final ExecutorContext INSTANCE = new ExecutorContext();
    private boolean spring;

    private ExecutorConfig config;

    private IpAddr myIp;
    private RpcClientManager schedulerIpManager;


    private ExecutorContext() {
    }

    public static ExecutorContext getInstance() {
        return INSTANCE;
    }

    public void init() throws IOException {
        config = PropertiesUtils.getConfig(CONFIG_FILEPATH, ExecutorConfig.class);
        InetAddress addr = InetAddress.getLocalHost();
        this.myIp = new IpAddr(addr.getHostAddress(), config.getPort());
    }

    public <T> T getSpringBean(Class<T> type) {
        return SpringContextHolder.getBean(type);
    }

    public void setSpringContext(ApplicationContext springContext) {
        SpringContextHolder.setApplicationContext(springContext);
        this.spring = true;
    }

    public Boolean isSpring() {
        return this.spring;
    }

    public IpAddr getMyIp() {
        return myIp;
    }

    public ExecutorConfig getConfig() {
        return config;
    }

    public RpcClientManager getSchedulerIpManager() {
        return schedulerIpManager;
    }
}
