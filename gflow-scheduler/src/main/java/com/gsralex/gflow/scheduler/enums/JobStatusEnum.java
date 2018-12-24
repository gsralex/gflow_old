package com.gsralex.gflow.scheduler.enums;

public enum JobStatusEnum {

    /**
     * 发送成功
     */
    SendOk(1),
    /**
     * 发送失败
     */
    SendErr(2),
    /**
     * 执行成功
     */
    ExecuteOk(3),
    /**
     * 执行失败
     */
    ExecuteErr(4);

    private int value;

    JobStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
