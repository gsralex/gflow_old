package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.gflow.executor.config.ExecutorConfig;
import com.gsralex.gflow.executor.connectclient.TExecutorClient;
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

    private TExecutorClient client;
    private boolean spring;

    private ExecutorConfig config;

    private List<IpAddress> schedulerIps=new ArrayList<>();

    public ExecutorContext() {
        client = new TExecutorClient(this);
    }

    public void init() throws IOException {
        config= PropertiesUtils.getConfig(CONFIG_FILEPATH,ExecutorConfig.class);
    }

    private void initIps(){
        String[] ips= config.getSchedulerIps().split(",");
        for(String ip:ips){
            String[] ipport= ip.split(":");
            IpAddress ipAddress=new IpAddress(ipport[0],Integer.parseInt(ipport[1]));
            schedulerIps.add(ipAddress);
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

    public List<IpAddress> getScheduleIps() {
        return schedulerIps;
    }

    public ExecutorConfig getConfig() {
        return config;
    }
}
