package com.gsralex.gflow.core.domain;

/**
 * @author gsralex
 * @date 2018/2/13
 */
public enum FlowBlanceEnum {
    LRU(1),
    LOAD(2);

    private int value;

    FlowBlanceEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
