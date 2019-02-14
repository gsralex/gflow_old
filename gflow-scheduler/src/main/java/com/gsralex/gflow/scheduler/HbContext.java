package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.hb.MExecutorHbProcess;
import com.gsralex.gflow.scheduler.hb.MSchedulerHbProcess;
import com.gsralex.gflow.scheduler.hb.SExecutorHbProcess;
import com.gsralex.gflow.scheduler.hb.SSchedulerHbProcess;

/**
 * @author gsralex
 * @version 2019/2/14
 */
public class HbContext {

    private MSchedulerHbProcess mSchedulerHbProcess;
    private SSchedulerHbProcess sSchedulerHbProcess;
    private MExecutorHbProcess mExecutorHbProcess;
    private SExecutorHbProcess sExecutorHbProcess;

    public MSchedulerHbProcess getmSchedulerHbProcess() {
        return mSchedulerHbProcess;
    }

    public void setmSchedulerHbProcess(MSchedulerHbProcess mSchedulerHbProcess) {
        this.mSchedulerHbProcess = mSchedulerHbProcess;
    }

    public SSchedulerHbProcess getsSchedulerHbProcess() {
        return sSchedulerHbProcess;
    }

    public void setsSchedulerHbProcess(SSchedulerHbProcess sSchedulerHbProcess) {
        this.sSchedulerHbProcess = sSchedulerHbProcess;
    }

    public MExecutorHbProcess getmExecutorHbProcess() {
        return mExecutorHbProcess;
    }

    public void setmExecutorHbProcess(MExecutorHbProcess mExecutorHbProcess) {
        this.mExecutorHbProcess = mExecutorHbProcess;
    }

    public SExecutorHbProcess getsExecutorHbProcess() {
        return sExecutorHbProcess;
    }

    public void setsExecutorHbProcess(SExecutorHbProcess sExecutorHbProcess) {
        this.sExecutorHbProcess = sExecutorHbProcess;
    }
}
