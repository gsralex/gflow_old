package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.spring.SpringContextHolder;
import com.gsralex.gflow.core.zk.SchedulerIpData;
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


    public <T> T getSpringBean(Class<T> type) {
        return SpringContextHolder.getBean(type);
    }

    public void setSpringApplicationContext(ApplicationContext springContext) {
        SpringContextHolder.setApplicationContext(springContext);
    }

    public List<IpAddress> getScheduleIps() {
        return ipData.getIps();
    }

    public GFlowContext getFlowContext(){
        return gFlowContext;
    }

}
