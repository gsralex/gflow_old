package com.gsralex.gflow.scheduler.time;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.domain.GFlowExecuteConfig;
import com.gsralex.gflow.scheduler.FlowService;
import com.gsralex.gflow.scheduler.TimeService;
import com.gsralex.gflow.scheduler.flow.FlowServiceImpl;

import java.util.List;

/**
 * 执行定时任务
 *
 * @author gsralex
 * @version 2018/8/14
 */
public class TimeProcess {

    private TimeService timeService;
    private FlowService flowService;
    private GFlowContext context;

    public TimeProcess(GFlowContext context) {
        this.context = context;
        this.flowService = new FlowServiceImpl(context);
        this.timeService = new TimeServiceImpl(context);
    }


    public void start() {
        while (true) {
            List<GFlowExecuteConfig> list = timeService.listNeedActionGroup();
            for (GFlowExecuteConfig config : list) {
                flowService.startGroup(config.getTriggerGroupId(), config.getParameter(), config.getId());
            }
        }
    }
}