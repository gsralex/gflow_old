package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.core.flow.FlowGuideMap;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class SchedulerContext {
    private static SchedulerContext currentContext = new SchedulerContext();

    private SchedulerContext() {
    }
    
    
    private static FlowGuideMap flowGuideMap =new FlowGuideMap();
    
    public static SchedulerContext getContext(){
        return currentContext;
    }
}
