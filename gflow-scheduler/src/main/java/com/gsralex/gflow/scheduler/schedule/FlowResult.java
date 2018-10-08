package com.gsralex.gflow.scheduler.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/10/8
 */
public class FlowResult {

    private List<ScheduleResult> nextResults;

    public FlowResult() {
        nextResults = new ArrayList<>();
    }

    public List<ScheduleResult> getNextResults() {
        return nextResults;
    }
}
