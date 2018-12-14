package com.gsralex.gflow.scheduler.time;

import com.gsralex.gflow.core.model.ExecuteConfig;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.enums.ExecuteTimeEnum;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimerTaskProcessorTest {

    @Test
    public void start() throws Exception {
        SchedulerContext context=new SchedulerContext();
        context.init();
        TimerTaskProcessor processor=new TimerTaskProcessor(context);
        processor.start();

        ExecuteConfig executeConfig=new ExecuteConfig();
        executeConfig.setActive(true);
        executeConfig.setTimeType(ExecuteTimeEnum.Time.getValue());
        executeConfig.setTime("00:24:00");
    }
}