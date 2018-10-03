package com.gsralex.gflow.scheduler;


import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.scheduler.retry.RetryProcessor;
import com.gsralex.gflow.scheduler.spring.SpringContextUtils;
import com.gsralex.gflow.scheduler.thrift.ThriftSchedulerServer;
import com.gsralex.gflow.scheduler.time.TimerTaskProcessor;

/**
 * @author gsralex
 * @date 2018/2/13
 */
public class SchedulerServer {

    public static void main(String[] args) {
        SchedulerServer server = new SchedulerServer();
        server.start();
    }

    public void start() {
        GFlowContext context = GFlowContext.getContext();
        context.initConfig();
        if (context.getConfig().getZkActive() != null && context.getConfig().getZkActive()) {
            context.initZk();
        }

        SpringContextUtils.init();

        SchedulerContext scheduleContext = new SchedulerContext();
        scheduleContext.setGflowContext(context);
        ThriftSchedulerServer server = new ThriftSchedulerServer();
        server.start();

        TimerTaskProcessor timeProcess = SpringContextUtils.getBean(TimerTaskProcessor.class);
        timeProcess.start();

        RetryProcessor retryProcessor = SpringContextUtils.getBean(RetryProcessor.class);
        retryProcessor.start();
    }
}

