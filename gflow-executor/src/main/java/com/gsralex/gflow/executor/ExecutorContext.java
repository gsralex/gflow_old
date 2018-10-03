package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.zk.SchedulerIpData;
import com.gsralex.gflow.executor.spring.SpringContextHolder;
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

    public static ExecutorContext getContext() {
        return currentContext;
    }

    public void setGflowContext(GFlowContext context) {
        gFlowContext = context;
        ipData = new SchedulerIpData(context);
    }


    private SpringContextHolder contextHolder = new SpringContextHolder();

    public <T> T getSpringBean(Class<T> type) {
        return contextHolder.getBean(type);
    }

    public boolean containsBean(Class type) {
        return contextHolder.containsBean(type);
    }

    public void setSpringApplicationContext(ApplicationContext springContext) {
        contextHolder.setApplicationContext(springContext);
    }

    public List<IpAddress> getScheduleIps() {
        return ipData.getIps();
    }

    public GFlowContext getFlowContext(){
        return gFlowContext;
    }

}
