package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.spring.SpringContextHolder;
import com.gsralex.gflow.core.zk.SchedulerIpData;
import com.gsralex.gflow.executor.thrift.TExecutorClient;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class ExecutorContext {
    private static ExecutorContext currentContext = new ExecutorContext();
    private SchedulerIpData ipData;
    private GFlowContext gFlowContext;
    private TExecutorClient client;
    private boolean spring;

    public static ExecutorContext getContext() {
        return currentContext;
    }

    public ExecutorContext() {
        client = new TExecutorClient(this);
    }

    public void setGflowContext(GFlowContext context) {
        gFlowContext = context;
        ipData = new SchedulerIpData(context);
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
        return ipData.getIps();
    }

    public GFlowContext getGFlowContext() {
        return gFlowContext;
    }

}
