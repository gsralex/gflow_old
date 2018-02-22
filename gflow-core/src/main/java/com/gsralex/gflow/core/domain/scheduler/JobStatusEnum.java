package com.gsralex.gflow.core.domain.scheduler;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public enum JobStatusEnum {
    OK(1),
    ERROR(2);

    private int value;

    JobStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
