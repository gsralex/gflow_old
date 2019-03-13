package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.hb.MasterRecvExecutorHbProcess;
import com.gsralex.gflow.scheduler.hb.MasterRecvSchedulerHbProcess;
import com.gsralex.gflow.scheduler.hb.SlaveSchedulerHbProcess;

/**
 * @author gsralex
 * @version 2019/2/14
 */
public class HbContext {

    private MasterRecvSchedulerHbProcess masterReceiveSchedulerHb;
    private SlaveSchedulerHbProcess slaveSchedulerHb;
    private MasterRecvExecutorHbProcess masterReceiveExecutorHb;

    public MasterRecvSchedulerHbProcess getMasterReceiveSchedulerHb() {
        return masterReceiveSchedulerHb;
    }

    public void setMasterReceiveSchedulerHb(MasterRecvSchedulerHbProcess masterReceiveSchedulerHb) {
        this.masterReceiveSchedulerHb = masterReceiveSchedulerHb;
    }

    public SlaveSchedulerHbProcess getSlaveSchedulerHb() {
        return slaveSchedulerHb;
    }

    public void setSlaveSchedulerHb(SlaveSchedulerHbProcess slaveSchedulerHb) {
        this.slaveSchedulerHb = slaveSchedulerHb;
    }

    public MasterRecvExecutorHbProcess getMasterReceiveExecutorHb() {
        return masterReceiveExecutorHb;
    }

    public void setMasterReceiveExecutorHb(MasterRecvExecutorHbProcess masterReceiveExecutorHb) {
        this.masterReceiveExecutorHb = masterReceiveExecutorHb;
    }
}
