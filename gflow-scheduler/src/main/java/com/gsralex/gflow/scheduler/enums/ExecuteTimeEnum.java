package com.gsralex.gflow.scheduler.enums;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public enum ExecuteTimeEnum {

    Time(1), Interval(2), TimeAndInterval(3);

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
                return ExecuteTimeEnum.Time;
            }
            case 2: {
                return ExecuteTimeEnum.Interval;
            }
            case 3: {
                return ExecuteTimeEnum.TimeAndInterval;
            }
            default: {
                return ExecuteTimeEnum.Time;
            }
        }
    }
}
