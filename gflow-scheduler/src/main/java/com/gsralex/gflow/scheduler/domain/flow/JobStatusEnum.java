package com.gsralex.gflow.scheduler.domain.flow;

public enum JobStatusEnum {

    SENDING(1), SENDOK(2), SENDERR(3), EXECUTING(4), FINISH(5);

    private int value;

    JobStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
