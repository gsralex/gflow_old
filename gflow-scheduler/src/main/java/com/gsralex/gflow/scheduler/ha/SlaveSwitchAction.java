package com.gsralex.gflow.scheduler.ha;

import com.gsralex.gflow.scheduler.HbContext;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.hb.SExecutorHbProcess;
import com.gsralex.gflow.scheduler.hb.SSchedulerHbProcess;

/**
 * @author gsralex
 * @version 2019/2/18
 */
public class SlaveSwitchAction implements SwitchAction {

    private SchedulerContext context = SchedulerContext.getInstance();

    @Override
    public void start() {
        if (context.getHbContext() == null) {
            context.setHbContext(new HbContext());
            //从调度
            SSchedulerHbProcess sSchedulerHbProcess = new SSchedulerHbProcess(context);
            sSchedulerHbProcess.start();
            context.getHbContext().setsSchedulerHbProcess(sSchedulerHbProcess);
            //主调度
            SExecutorHbProcess sExecutorHbProcess = new SExecutorHbProcess();
            context.getHbContext().setsExecutorHbProcess(sExecutorHbProcess);
        } else {
            context.getHbContext().getsSchedulerHbProcess().start();
        }
    }

    @Override
    public void stop() {
        SSchedulerHbProcess sSchedulerHbProcess = context.getHbContext().getsSchedulerHbProcess();
        sSchedulerHbProcess.stop();
    }
}
