package com.gsralex.gflow.scheduler;


import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.scheduler.retry.RetryProcessor;
import com.gsralex.gflow.core.spring.SpringContextHolder;
import com.gsralex.gflow.scheduler.spring.SpringConfiguration;
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

        SpringContextHolder.init(SpringConfiguration.class);
        SchedulerContext scheduleContext = SchedulerContext.getContext();
        scheduleContext.setGflowContext(context);
        ThriftSchedulerServer server = SpringContextHolder.getBean(ThriftSchedulerServer.class);
        server.start();

        //定时任务
        TimerTaskProcessor timeProcess = SpringContextHolder.getBean(TimerTaskProcessor.class);
        scheduleContext.setTimerTaskProcessor(timeProcess);
        timeProcess.start();

        //当开启重试的时候才做重试任务执行
        if (context.getConfig().getRetryActive() != null && context.getConfig().getRetryActive()) {
            RetryProcessor retryProcessor = SpringContextHolder.getBean(RetryProcessor.class);
            scheduleContext.setRetryProcessor(retryProcessor);
            retryProcessor.start();
        }
    }
}

