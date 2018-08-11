package com.gsralex.gflow.scheduler.thrift;


import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thrift.gen.TGroupJobDesc;
import com.gsralex.gflow.core.thrift.gen.TJobDesc;
import com.gsralex.gflow.core.thrift.gen.TResult;
import com.gsralex.gflow.core.thrift.gen.TScheduleService;
import com.gsralex.gflow.scheduler.service.FlowService;
import com.gsralex.gflow.scheduler.service.impl.FlowServiceImpl;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TScheduleServiceImpl implements TScheduleService.Iface {


    private FlowService flowService;

    private GFlowContext context;


    public TScheduleServiceImpl(GFlowContext context) {
        this.context = context;
        this.flowService = new FlowServiceImpl(context);
    }

    @Override
    public TResult schedule(TJobDesc desc) throws TException {
        flowService.startAction(desc.getActionId(), desc.getParameter());
        TResult tResult = new TResult();
        tResult.setOk(true);
        return tResult;
    }

    @Override
    public TResult scheduleGroup(TGroupJobDesc desc) throws TException {
        flowService.startGroup(desc.getGroupId(), desc.getParameter(), 0);
        TResult tResult = new TResult();
        tResult.setOk(true);
        return tResult;
    }

    @Override
    public TResult ack(long jobId, boolean ok) throws TException {
        flowService.actionAck(jobId, ok);
        TResult tResult = new TResult();
        tResult.setOk(true);
        return tResult;
    }

}
