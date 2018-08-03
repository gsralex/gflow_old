package com.gsralex.gflow.scheduler.context;

import com.gsralex.gflow.scheduler.rpc.RpcClient;

public class RpcContext {

    private RpcClient rpcClient;

    public RpcContext(RpcClient rpcClient){
        this.rpcClient=rpcClient;
    }
}
