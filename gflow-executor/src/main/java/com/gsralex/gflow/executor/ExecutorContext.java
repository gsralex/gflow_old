package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.gflow.executor.config.ExecutorConfig;
import com.gsralex.gflow.executor.client.TExecutorClient;
import com.gsralex.gflow.executor.spring.SpringContextHolder;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class ExecutorContext {

    private static final String CONFIG_FILEPATH = "/gflow.properties";

    private static final ExecutorContext current = new ExecutorContext();

    private TExecutorClient client;
    private boolean spring;

    private ExecutorConfig config;

    private List<IpAddr> schedulerIps = new ArrayList<>();

    private ExecutorContext() {

    }

    public static ExecutorContext getInstance() {
        return current;
    }

    public void init() throws IOException {
        client = new TExecutorClient(this);
        config = PropertiesUtils.getConfig(CONFIG_FILEPATH, ExecutorConfig.class);
        initIps();
    }

    private void initIps() {
        String[] ips = config.getSchedulerIps().split(",");
        for (String ip : ips) {
            String[] ipport = ip.split(":");
            IpAddr ipAddr = new IpAddr(ipport[0], Integer.parseInt(ipport[1]));
            schedulerIps.add(ipAddr);
        }
    }


    public void ack(long id, boolean ok) {
        client.ack(id, ok);
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

    public List<IpAddr> getScheduleIps() {
        return schedulerIps;
    }

    public ExecutorConfig getConfig() {
        return config;
    }
}
