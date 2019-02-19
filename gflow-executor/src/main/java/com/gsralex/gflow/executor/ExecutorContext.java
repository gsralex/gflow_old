package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.hb.SchedulerHbProcess;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.util.PropertiesUtils;
import com.gsralex.gflow.executor.config.ExecutorConfig;
import com.gsralex.gflow.executor.spring.SpringContextHolder;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class ExecutorContext {

    private static final String CONFIG_FILEPATH = "/gflow.properties";

    private static final ExecutorContext current = new ExecutorContext();
    private boolean spring;

    private ExecutorConfig config;

    private IpAddr myIp;

    private String accessToken;

    private SchedulerHbProcess schedulerHbProcess;

    private ExecutorContext() {
    }

    public static ExecutorContext getInstance() {
        return current;
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

    public String getAccessToken() {
        return accessToken;
    }

    public SchedulerHbProcess getSchedulerHbProcess() {
        return schedulerHbProcess;
    }

    public void setSchedulerHbProcess(SchedulerHbProcess schedulerHbProcess) {
        this.schedulerHbProcess = schedulerHbProcess;
    }
}
