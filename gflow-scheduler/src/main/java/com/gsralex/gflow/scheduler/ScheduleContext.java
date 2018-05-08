package com.gsralex.gflow.scheduler;


import com.gsralex.gflow.scheduler.context.GFlowContext;
import com.gsralex.gflow.scheduler.rpc.RpcClient;
import com.gsralex.gflow.scheduler.thrift.TRpcClient;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class ScheduleContext {

    private RpcClient rpcClient;

    private GFlowContext flowContext;

    public ScheduleContext(GFlowContext flowContext) {
        this.flowContext = flowContext;
        this.rpcClient = new TRpcClient();
    }

    public GFlowContext getFlowContext() {
        return flowContext;
    }

    public RpcClient getRpcClient() {
        return rpcClient;
    }

}
