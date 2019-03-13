package com.gsralex.gflow.scheduler.ha;

import com.gsralex.gflow.scheduler.HbContext;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.hb.SlaveSchedulerHbProcess;

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
            SlaveSchedulerHbProcess slaveSchedulerHb = new SlaveSchedulerHbProcess(context);
            slaveSchedulerHb.start();
            context.getHbContext().setSlaveSchedulerHb(slaveSchedulerHb);
        } else {
            context.getHbContext().getSlaveSchedulerHb().start();
        }
    }

    @Override
    public void stop() {
        SlaveSchedulerHbProcess slaveSchedulerHb = context.getHbContext().getSlaveSchedulerHb();
        slaveSchedulerHb.stop();
    }
}
