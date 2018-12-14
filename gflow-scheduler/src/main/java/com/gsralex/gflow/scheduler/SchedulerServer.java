package com.gsralex.gflow.scheduler;


import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.spring.SpringContextHolder;
import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.retry.RetryProcessor;
import com.gsralex.gflow.scheduler.server.ScheduleTransportException;
import com.gsralex.gflow.scheduler.server.ThriftSchedulerServer;
import com.gsralex.gflow.scheduler.time.TimerTaskProcessor;
import org.apache.log4j.Logger;

/**
 * @author gsralex
 * @date 2018/2/13
 */
public class SchedulerServer {

    private static final Logger LOGGER = Logger.getLogger(SchedulerServer.class);

    /**
     * 重试处理器
     */
    private RetryProcessor retryProcessor;
    /**
     * 任务定时处理器
     */
    private TimerTaskProcessor timerTaskProcessor;


    public void addParameter(DynamicParam parameter) {
        SchedulerContext context = SchedulerContext.getContext();
        context.addParam(parameter);
    }

    public void serve() throws ScheduleTransportException {
        LOGGER.info("====== SchedulerServer STARTING ======");
        GFlowContext context = GFlowContext.getContext();
        context.initConfig();
        if (context.getConfig().getZkActive() != null && context.getConfig().getZkActive()) {
            context.initZk();
        }
        SchedulerContext scheduleContext = SchedulerContext.getContext();
        scheduleContext.setGflowContext(context);
        ThriftSchedulerServer server = new ThriftSchedulerServer(scheduleContext);
        server.start();
        LOGGER.info("====== SchedulerServer.serve STARTED ======");
        //retryProcessor = new RetryProcessor(scheduleContext);
        //timerTaskProcessor = new TimerTaskProcessor(scheduleContext);

    }

    public static void main(String[] args) throws ScheduleTransportException {
        SchedulerServer server = new SchedulerServer();
        server.serve();
    }
}

