package com.gsralex.gflow.scheduler.impl;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.domain.GFlowExecuteConfig;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.scheduler.FlowService;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author gsralex
 * @version 2018/8/20
 */
public class TimerTask extends Thread {

    private GFlowContext context;
    private GFlowExecuteConfig config;
    private FlowService flowService;
    private boolean timeExecuted;

    public TimerTask(GFlowContext context, GFlowExecuteConfig config) {
        this.context = context;
        this.config = config;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            flowService.startGroup(config.getTriggerGroupId(), "", config.getId());
            long countdown = getNextTimeInterval(config.getTime());
            try {
                Thread.sleep(countdown);
            } catch (InterruptedException e) {
                break; //中断
            }
            flowService.startGroup(config.getTriggerGroupId(), "", config.getId());
        }
    }

    private long getNextTimeInterval(String time) {
        Date executeDate = DtUtils.getTodayTime(time);
        if (executeDate.getTime() < System.currentTimeMillis()) {
            executeDate = DateUtils.addDays(executeDate, 1);
        }
        return executeDate.getTime() - System.currentTimeMillis();
    }
}
