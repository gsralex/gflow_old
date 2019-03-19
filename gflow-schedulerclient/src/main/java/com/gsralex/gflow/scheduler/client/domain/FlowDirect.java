package com.gsralex.gflow.scheduler.client.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class FlowDirect {
    private List<Integer> nextIndex = new ArrayList<>();
    private int index;

    public FlowDirect index(int index) {
        this.index = index;
        return this;
    }

    public FlowDirect next(int... index) {
        for (int i : index) {
            nextIndex.add(i);
        }
        return this;
    }

    public List<Integer> getNextIndex() {
        return nextIndex;
    }

    public int getIndex() {
        return index;
    }
}
