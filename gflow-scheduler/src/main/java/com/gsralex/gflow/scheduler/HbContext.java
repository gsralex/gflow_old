package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.hb.MasterReceiveExecutorHb;
import com.gsralex.gflow.scheduler.hb.MasterReceiveSchedulerHb;
import com.gsralex.gflow.scheduler.hb.SlaveSchedulerHb;

/**
 * @author gsralex
 * @version 2019/2/14
 */
public class HbContext {

    private MasterReceiveSchedulerHb masterReceiveSchedulerHb;
    private SlaveSchedulerHb slaveSchedulerHb;
    private MasterReceiveExecutorHb masterReceiveExecutorHb;

    public MasterReceiveSchedulerHb getMasterReceiveSchedulerHb() {
        return masterReceiveSchedulerHb;
    }

    public void setMasterReceiveSchedulerHb(MasterReceiveSchedulerHb masterReceiveSchedulerHb) {
        this.masterReceiveSchedulerHb = masterReceiveSchedulerHb;
    }

    public SlaveSchedulerHb getSlaveSchedulerHb() {
        return slaveSchedulerHb;
    }

    public void setSlaveSchedulerHb(SlaveSchedulerHb slaveSchedulerHb) {
        this.slaveSchedulerHb = slaveSchedulerHb;
    }

    public MasterReceiveExecutorHb getMasterReceiveExecutorHb() {
        return masterReceiveExecutorHb;
    }

    public void setMasterReceiveExecutorHb(MasterReceiveExecutorHb masterReceiveExecutorHb) {
        this.masterReceiveExecutorHb = masterReceiveExecutorHb;
    }
}
