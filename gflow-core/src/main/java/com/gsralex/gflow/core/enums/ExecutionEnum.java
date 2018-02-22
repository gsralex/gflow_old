package com.gsralex.gflow.core.enums;

/**
 * @author gsralex
 * @date 2018/2/18
 */
public enum ExecutionEnum {

    INTEVAL(1),
    TIMINGDAY(2),
    TIMINGWEEK(3),
    TIMINGMONTH(4);

    private int value;

    ExecutionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ExecutionEnum valueOf(int value) {
        switch (value) {
            case 1: {
                return ExecutionEnum.INTEVAL;
            }
            case 2: {
                return ExecutionEnum.TIMINGDAY;
            }
            case 3: {
                return ExecutionEnum.TIMINGWEEK;
            }
            case 4: {
                return ExecutionEnum.TIMINGMONTH;
            }
            default: {
                return ExecutionEnum.INTEVAL;
            }
        }
    }
}
