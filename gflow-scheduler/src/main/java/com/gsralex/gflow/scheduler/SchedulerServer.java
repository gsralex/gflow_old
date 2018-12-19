package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.server.ScheduleTransportException;
import com.gsralex.gflow.scheduler.server.ThriftSchedulerServer;
import com.gsralex.gflow.scheduler.timer.TimerProcessor;
import com.gsralex.gflow.scheduler.timer.TimerRecovery;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gsralex
 * @date 2018/2/13
 */
public class SchedulerServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerServer.class);

    private SchedulerContext context;

    public SchedulerServer() throws IOException {
        context = new SchedulerContext();
        context.init();
    }

    public void addParameter(DynamicParam parameter) {
        context.addParam(parameter);
    }

    public void serve() throws ScheduleTransportException, IOException {
        LOGGER.info("====== SchedulerServer STARTING ======");

        ThriftSchedulerServer server = new ThriftSchedulerServer(context);
        server.start();
        LOGGER.info("====== SchedulerServer.serve STARTED ======");

        TimerProcessor timerProcessor = TimerProcessor.getInstance();
        timerProcessor.setContext(context);
        timerProcessor.start();
        TimerRecovery timerRecovery = new TimerRecovery(timerProcessor, context);
        timerRecovery.recovery();

    }

    public static void main(String[] args) throws ScheduleTransportException, IOException {
        SchedulerServer server = new SchedulerServer();
        server.addParameter(getBizdataParam());
        server.serve();
    }

    private static DynamicParam getBizdataParam() {
        return new DynamicParam() {
            @Override
            public String getValue(String key) {
                Pattern pattern = Pattern.compile("bizdate(-(?<day>\\d+)d)?");
                Matcher matcher = pattern.matcher(key);
                matcher.find();
                int day = NumberUtils.toInt(matcher.group("day"), 0);
                return DateFormatUtils.format(DateUtils.addDays(new Date(), day), "yyyyMMdd");
            }
        };
    }
}

