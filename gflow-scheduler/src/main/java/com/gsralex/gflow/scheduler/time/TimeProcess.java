package com.gsralex.gflow.scheduler.time;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.scheduler.FlowService;
import com.gsralex.gflow.scheduler.flow.FlowServiceImpl;
import com.gsralex.gflow.scheduler.impl.TimerTask;

import java.util.Map;

/**
 * 执行定时任务
 *
 * @author gsralex
 * @version 2018/8/14
 */
public class TimeProcess {

    private FlowService flowService;
    private GFlowContext context;


    /**
     * 今日执行次数 key:20180820，value->key:configId,value:executeCnt
     */
    private Map<Integer, Map<Long, Integer>> timeMap;

    public TimeProcess(GFlowContext context) {
        this.context = context;
        this.flowService = new FlowServiceImpl(context);
    }

    public void start() {
        
    }
}