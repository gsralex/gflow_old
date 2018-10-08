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

    public static JobGroupStatusEnum valueOf(int value) {
        switch (value) {
            case 1: {
                return EXECUTING;
            }
            case 2: {
                return PAUSE;
            }
            case 3: {
                return STOP;
            }
            case 4: {
                return FINISH;
            }
            default: {
                return EXECUTING;
            }
        }
    }

}
