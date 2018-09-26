package com.gsralex.gflow.scheduler;


import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.ScheduleContext;
import com.gsralex.gflow.scheduler.retry.RetryProcessor;
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
        context.init();

        ScheduleContext scheduleContext = new ScheduleContext();
        context.setScheduleContext(scheduleContext);

        ThriftSchedulerServer server = new ThriftSchedulerServer(context);
        server.start();

        TimerTaskProcessor timeProcess = new TimerTaskProcessor();
        timeProcess.start();

        RetryProcessor retryProcessor = new RetryProcessor(context);
        retryProcessor.start();
    }
}

