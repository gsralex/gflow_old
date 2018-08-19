package com.gsralex.gflow.core.context;

import com.gsralex.gflow.core.flow.FlowGuide;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/8/18
 */

public class ScheduleContext {

    private Map<Long, FlowGuide> flowGuideMap;

    public ScheduleContext() {
        this.flowGuideMap = new HashMap<>();
    }

    public void putFlowMap(long jobGroupId, FlowGuide flowGuide) {
        flowGuideMap.put(jobGroupId, flowGuide);
    }

    public FlowGuide getFlowMap(long jobGroupId) {
        return flowGuideMap.get(jobGroupId);
    }

    public void finishFlowMap(long jobId) {
        flowGuideMap.remove(jobId);
    }
}
