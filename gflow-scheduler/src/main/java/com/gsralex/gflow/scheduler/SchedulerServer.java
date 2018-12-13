package com.gsralex.gflow.scheduler;


import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.core.spring.SpringContextHolder;
import com.gsralex.gflow.scheduler.spring.SpringConfiguration;
import com.gsralex.gflow.scheduler.server.ScheduleTransportException;
import com.gsralex.gflow.scheduler.server.ThriftSchedulerServer;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author gsralex
 * @date 2018/2/13
 */
public class SchedulerServer {

    public static void main(String[] args) throws ScheduleTransportException {
        SchedulerServer server = new SchedulerServer();
        server.start();
    }

    public static class BizDateDynamicParam implements DynamicParam {

        @Override
        public String getRegexKey() {
            return "bizdate";
        }

        @Override
        public String getValue(String key) {
            return DateFormatUtils.format(DateUtils.addDays(new Date(), -2), "yyyyMMdd");
        }
    }


    public void addParameter(DynamicParam parameter) {
        SchedulerContext context = SchedulerContext.getContext();
        context.addParam(parameter);
    }

    public void start() throws ScheduleTransportException {
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
//        TimerTaskProcessor timeProcess = SpringContextHolder.getBean(TimerTaskProcessor.class);
//        scheduleContext.setTimerTaskProcessor(timeProcess);
//        timeProcess.start();
//
//        //当开启重试的时候才做重试任务执行
//        if (context.getConfig().getRetryActive() != null && context.getConfig().getRetryActive()) {
//            RetryProcessor retryProcessor = SpringContextHolder.getBean(RetryProcessor.class);
//            scheduleContext.setRetryProcessor(retryProcessor);
//            retryProcessor.start();
//        }
    }
}

