package com.gsralex.gflow.scheduler.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/10/8
 */
public class FlowResult {

    private List<ActionResult> results;

    public FlowResult() {
        results = new ArrayList<>();
    }

    public List<ActionResult> getResults() {
        return results;
    }
}
