package com.gsralex.gflow.scheduler.ha;

import com.gsralex.gflow.scheduler.HbContext;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.hb.MasterRecvExecutorHbProcess;
import com.gsralex.gflow.scheduler.hb.MasterRecvSchedulerHbProcess;
import com.gsralex.gflow.scheduler.timer.TimerProcess;
import com.gsralex.gflow.scheduler.timer.TimerRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gsralex
 * @version 2019/2/15
 */
@Component
public class MasterSwitchAction implements SwitchAction {

    private SchedulerContext context=SchedulerContext.getInstance();
    @Autowired
    private TimerRecovery recovery;


    @Override
    public void start() {
        if (context.getHbContext() == null) {
            context.setHbContext(new HbContext());
            MasterRecvSchedulerHbProcess masterReceiveSchedulerHb = new MasterRecvSchedulerHbProcess(context);
            masterReceiveSchedulerHb.start();
            context.getHbContext().setMasterReceiveSchedulerHb(masterReceiveSchedulerHb);

            MasterRecvExecutorHbProcess masterReceiveExecutorHb = new MasterRecvExecutorHbProcess(context);
            masterReceiveExecutorHb.start();
            context.getHbContext().setMasterReceiveExecutorHb(masterReceiveExecutorHb);
            //定时任务
            TimerProcess timerProcess = new TimerProcess();
            timerProcess.start();
            context.setTimerProcess(timerProcess);

            recovery.recover();
        } else {
            context.getHbContext().getMasterReceiveSchedulerHb().start();
            context.getHbContext().getMasterReceiveExecutorHb().start();
        }
    }

    @Override
    public void stop() {
        context.getHbContext().getMasterReceiveSchedulerHb().stop();
        context.getHbContext().getMasterReceiveExecutorHb().stop();
    }
}
