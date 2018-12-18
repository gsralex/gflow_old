package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.server.ScheduleTransportException;
import com.gsralex.gflow.scheduler.server.ThriftSchedulerServer;
import com.gsralex.gflow.scheduler.timer.TimerProcessor;
import com.gsralex.gflow.scheduler.timer.TimerRecovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author gsralex
 * @date 2018/2/13
 */
public class SchedulerServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerServer.class);

    private SchedulerContext context;

    public void addParameter(DynamicParam parameter) {
        context.addParam(parameter);
    }

    public void serve() throws ScheduleTransportException, IOException {
        LOGGER.info("====== SchedulerServer STARTING ======");
        context = new SchedulerContext();
        context.init();

        ThriftSchedulerServer server = new ThriftSchedulerServer(context);
        server.start();
        LOGGER.info("====== SchedulerServer.serve STARTED ======");

        TimerProcessor timerProcessor = TimerProcessor.getInstance();
        timerProcessor.setContext(context);
        TimerRecovery timerRecovery = new TimerRecovery(timerProcessor, context);
        timerRecovery.reply();

    }

    public static void main(String[] args) throws ScheduleTransportException, IOException {
        SchedulerServer server = new SchedulerServer();
        server.serve();
    }
}

