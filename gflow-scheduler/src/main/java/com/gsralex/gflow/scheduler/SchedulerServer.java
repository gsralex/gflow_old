package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.retry.RetryProcessor;
import com.gsralex.gflow.scheduler.server.ScheduleTransportException;
import com.gsralex.gflow.scheduler.server.ThriftSchedulerServer;
import com.gsralex.gflow.scheduler.timer.TimerTaskProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author gsralex
 * @date 2018/2/13
 */
public class SchedulerServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerServer.class);

    /**
     * 重试处理器
     */
    private RetryProcessor retryProcessor;
    /**
     * 任务定时处理器
     */
    private TimerTaskProcessor timerTaskProcessor;

    private SchedulerContext context;

    public void addParameter(DynamicParam parameter) {
        context.addParam(parameter);
    }

    public void serve() throws ScheduleTransportException, IOException {
        LOGGER.info("====== SchedulerServer STARTING ======");
        SchedulerContext context = new SchedulerContext();
        context.init();

        ThriftSchedulerServer server = new ThriftSchedulerServer(context);
        server.start();
        LOGGER.info("====== SchedulerServer.serve STARTED ======");
        //retryProcessor = new RetryProcessor(scheduleContext);
        //timerTaskProcessor = new TimerTaskProcessor(scheduleContext);

    }

    public static void main(String[] args) throws ScheduleTransportException, IOException {
        SchedulerServer server = new SchedulerServer();
        server.serve();
    }
}

