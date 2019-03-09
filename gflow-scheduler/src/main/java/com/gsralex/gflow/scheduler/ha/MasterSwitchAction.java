package com.gsralex.gflow.scheduler.ha;

import com.gsralex.gflow.scheduler.HbContext;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.hb.MExecutorHbProcess;
import com.gsralex.gflow.scheduler.hb.MSchedulerHbProcess;
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
            MSchedulerHbProcess mSchedulerHbProcess = new MSchedulerHbProcess(context);
            mSchedulerHbProcess.start();
            context.getHbContext().setmSchedulerHbProcess(mSchedulerHbProcess);

            MExecutorHbProcess mExecutorHbProcess = new MExecutorHbProcess(context);
            mExecutorHbProcess.start();
            context.getHbContext().setmExecutorHbProcess(mExecutorHbProcess);

            TimerProcess timerProcess = new TimerProcess();
            timerProcess.start();
            context.setTimerProcess(timerProcess);
        } else {
            context.getHbContext().getmSchedulerHbProcess().start();
            context.getHbContext().getmExecutorHbProcess().start();
        }
    }

    @Override
    public void stop() {
        context.getHbContext().getmSchedulerHbProcess().stop();
        context.getHbContext().getsSchedulerHbProcess().stop();
    }
}
