package com.gsralex.gflow.scheduler.timer;

import com.gsralex.gflow.core.domain.TimerConfigPo;
import com.gsralex.gflow.scheduler.SchedulerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author gsralex
 * @version 2018/12/17
 */
public class TimerProcessTest {
    @Test
    public void getMin() throws Exception {

        SchedulerContext.getInstance().init();
        TimerProcess.TimerHandler handler = new TimerProcess.TimerHandler();

        //设置现在时间
        Calendar now = Calendar.getInstance();
        now.set(2018, 11, 17, 3, 0, 0);
        Date date = now.getTime();

        TimerConfigPo tc1 = new TimerConfigPo();
        tc1.setId(1L);
        tc1.setTime("06:00:00");
        tc1.setActive(true);
        TimerTask task1 = new TimerTask(tc1);
        handler.setTimer(task1);

        TimerConfigPo tc2 = new TimerConfigPo();
        tc2.setId(2L);
        tc2.setTime("05:00:00");
        tc2.setActive(true);
        TimerTask task2 = new TimerTask(tc2);
        handler.setTimer(task2);


        TimerTask minTask = handler.getMin(date);
        Assert.assertEquals(minTask, task2);


        //早于当前时间
        TimerConfigPo tc3 = new TimerConfigPo();
        tc3.setId(3L);
        tc3.setTime("02:30:00"); //早于现在时间
        tc3.setActive(true);
        TimerTask task3 = new TimerTask(tc3);
        handler.setTimer(task3);
        TimerTask minTask2 = handler.getMin(date);
        Assert.assertEquals(minTask2, task3);

        //早于当前时间，但是执行过,执行时间将会推迟到第二天
        handler.setTimer(new TimerTask(tc3, now.getTimeInMillis()));
        TimerTask minTask3 = handler.getMin(date);
        Assert.assertEquals(minTask3, task2);


    }


    @Test
    public void getInterval() throws Exception {

    }

}