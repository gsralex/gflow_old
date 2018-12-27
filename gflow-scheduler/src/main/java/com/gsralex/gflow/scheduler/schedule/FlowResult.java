package com.gsralex.gflow.scheduler.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/10/8
 */
public class FlowResult {

    private List<ActionResult> results;

    private Long jobGroupId;

    public FlowResult() {
        results = new ArrayList<>();
    }

    public Long getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(Long jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public List<ActionResult> getResults() {
        return results;
    }
}
