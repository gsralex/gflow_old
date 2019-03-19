package com.gsralex.gflow.core.enums;

public enum JobGroupStatus {

    EXECUTING(1),
    PAUSE(2),
    STOP(3),
    FINISH(4);

    private int value;

    JobGroupStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static JobGroupStatus valueOf(int value) {
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
