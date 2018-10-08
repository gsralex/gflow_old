package com.gsralex.gflow.scheduler.flow;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/8/22
 */
public class FlowGuideMap {

    private Map<Long, FlowGuide> flowGuideMap;

    public FlowGuideMap() {
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
