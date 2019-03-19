package com.gsralex.gflow.core.enums;

public enum JobStatus {

    /**
     * 正在执行
     */
    Executing(1),
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

    JobStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
