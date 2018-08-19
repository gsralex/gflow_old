package com.gsralex.gflow.core.enums;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public enum ExecuteTimeEnum {

    SetTime(1), Interval(2);

    private int value;

    ExecuteTimeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ExecuteTimeEnum valueOf(int value) {
        switch (value) {
            case 1: {
                return ExecuteTimeEnum.SetTime;
            }
            case 2: {
                return ExecuteTimeEnum.Interval;
            }
            default: {
                return ExecuteTimeEnum.SetTime;
            }
        }
    }
}
