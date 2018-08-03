package com.gsralex.gflow.scheduler.domain.flow;

public enum JobStatusEnum {

    Start(1),Pause(2),Stop(3);

    private int value;
    JobStatusEnum(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
