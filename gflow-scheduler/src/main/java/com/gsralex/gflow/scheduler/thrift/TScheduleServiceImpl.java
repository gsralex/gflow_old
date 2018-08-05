package com.gsralex.gflow.scheduler.thrift;


import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thrift.gen.TGroupJobDesc;
import com.gsralex.gflow.core.thrift.gen.TJobDesc;
import com.gsralex.gflow.core.thrift.gen.TResult;
import com.gsralex.gflow.core.thrift.gen.TScheduleService;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TScheduleServiceImpl implements TScheduleService.Iface {


    private GFlowContext context;

    public TScheduleServiceImpl(GFlowContext context) {
        this.context = context;
    }

    @Override
    public TResult schedule(TJobDesc desc) throws TException {
        return null;
    }

    @Override
    public TResult scheduleGroup(TGroupJobDesc desc) throws TException {
        return null;
    }

    @Override
    public TResult ack(TJobDesc desc, boolean ok) throws TException {
        return null;
    }
}
