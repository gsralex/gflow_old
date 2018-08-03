package com.gsralex.gflow.scheduler.domain.flow;

public enum JobGroupStatusEnum {

    EXECUTING(1),PAUSE(2),STOP(3);

    private int value;
    JobGroupStatusEnum(int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }

}