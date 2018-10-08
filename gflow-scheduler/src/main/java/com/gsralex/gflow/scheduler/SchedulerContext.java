package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.scheduler.flow.FlowGuideMap;
import com.gsralex.gflow.core.zk.ExecutorIpData;
import com.gsralex.gflow.scheduler.retry.RetryProcessor;
import com.gsralex.gflow.scheduler.time.TimerTaskProcessor;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class SchedulerContext {
    private static SchedulerContext currentContext = new SchedulerContext();

    private GFlowContext gFlowContext;
    private ExecutorIpData ipData;

    private RetryProcessor retryProcessor;
    private TimerTaskProcessor timerTaskProcessor;

    private FlowGuideMap flowGuideMap = new FlowGuideMap();

    private SchedulerContext() {

    }

    public void setGflowContext(GFlowContext context) {
        gFlowContext = context;
        this.ipData = new ExecutorIpData(context);
    }


    public static SchedulerContext getContext() {
        return currentContext;
    }

    public GFlowContext getGFlowContext() {
        return gFlowContext;
    }

    public List<IpAddress> getIps() {
        return ipData.getIps();
    }

    public RetryProcessor getRetryProcessor() {
        return retryProcessor;
    }

    public void setRetryProcessor(RetryProcessor retryProcessor) {
        this.retryProcessor = retryProcessor;
    }

    public TimerTaskProcessor getTimerTaskProcessor() {
        return timerTaskProcessor;
    }

    public void setTimerTaskProcessor(TimerTaskProcessor timerTaskProcessor) {
        this.timerTaskProcessor = timerTaskProcessor;
    }

    public FlowGuideMap getFlowGuideMap() {
        return flowGuideMap;
    }
}
