package com.gsralex.gflow.core.enums;

public enum JobGroupStatusEnum {

    EXECUTING(1), PAUSE(2), STOP(3), FINISH(4);

    private int value;

    JobGroupStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
