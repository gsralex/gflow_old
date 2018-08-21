package com.gsralex.gflow.scheduler.thrift;


import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thriftgen.TGroupJobDesc;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import com.gsralex.gflow.scheduler.FlowService;
import com.gsralex.gflow.scheduler.flow.FlowServiceImpl;
import org.apache.thrift.TException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TScheduleServiceImpl implements TScheduleService.Iface {


    private FlowService flowService;

    private GFlowContext context;

    private ExecutorService threadPool;


    public TScheduleServiceImpl(GFlowContext context) {
        this.context = context;
        this.flowService = new FlowServiceImpl(context);
        this.threadPool = Executors.newCachedThreadPool();
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
}
