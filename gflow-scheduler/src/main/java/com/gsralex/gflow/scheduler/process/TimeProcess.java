package com.gsralex.gflow.scheduler.process;

import com.gsralex.gflow.scheduler.context.GFlowContext;
import com.gsralex.gflow.scheduler.domain.flow.GFlowExecuteConfig;
import com.gsralex.gflow.scheduler.service.ExecuteService;
import com.gsralex.gflow.scheduler.service.FlowService;
import com.gsralex.gflow.scheduler.service.impl.ExecuteServiceImpl;
import com.gsralex.gflow.scheduler.service.impl.FlowServiceImpl;

import java.util.List;

public class TimeProcess {

    private ExecuteService executeService;
    private FlowService flowService;
    private GFlowContext context;
    public TimeProcess(GFlowContext context){
        this.context=context;
        this.executeService=new ExecuteServiceImpl(context);
        this.flowService=new FlowServiceImpl(context);
    }


    public void start(){
        while(true) {
            List<GFlowExecuteConfig> list= executeService.getNeedExecuteConfig();
            for(GFlowExecuteConfig config:list){
                flowService.startGroup(config.getTriggerGroupId(),config.getParameter(),config.getId());
            }
        }
    }


}
