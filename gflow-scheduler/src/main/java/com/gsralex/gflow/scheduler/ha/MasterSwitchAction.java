package com.gsralex.gflow.scheduler.ha;

import com.gsralex.gflow.scheduler.HbContext;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.hb.MasterReceiveExecutorHb;
import com.gsralex.gflow.scheduler.hb.MasterReceiveSchedulerHb;
import com.gsralex.gflow.scheduler.timer.TimerProcess;

/**
 * @author gsralex
 * @version 2019/2/15
 */
public class MasterSwitchAction implements SwitchAction {

    private SchedulerContext context = SchedulerContext.getInstance();

    @Override
    public void start() {
        if (context.getHbContext() == null) {
            context.setHbContext(new HbContext());
            MasterReceiveSchedulerHb masterReceiveSchedulerHb = new MasterReceiveSchedulerHb(context);
            masterReceiveSchedulerHb.start();
            context.getHbContext().setMasterReceiveSchedulerHb(masterReceiveSchedulerHb);

            MasterReceiveExecutorHb masterReceiveExecutorHb = new MasterReceiveExecutorHb(context);
            masterReceiveExecutorHb.start();
            context.getHbContext().setMasterReceiveExecutorHb(masterReceiveExecutorHb);
            //定时任务
            TimerProcess timerProcess = new TimerProcess();
            timerProcess.start();
            context.setTimerProcess(timerProcess);
        } else {
            context.getHbContext().getMasterReceiveSchedulerHb().start();
            context.getHbContext().getMasterReceiveExecutorHb().start();
        }
    }

    @Override
    public void stop() {
        context.getHbContext().getMasterReceiveSchedulerHb().stop();
        context.getHbContext().getSlaveSchedulerHb().stop();
    }
}
