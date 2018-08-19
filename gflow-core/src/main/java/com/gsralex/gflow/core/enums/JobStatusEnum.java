package com.gsralex.gflow.core.enums;

public enum JobStatusEnum {

    Sending(1), SendErr(2), Executing(3), ExecuteOk(4), ExecuteFailed(5);

    private int value;

    JobStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
