package com.gsralex.gflow.scheduler.rpc;

import com.gsralex.gflow.scheduler.thrift.TRpcClient;

/**
 * @author gsralex
 * @date 2018/2/18
 */
public class RpcClientFactory {

    public static RpcClient createThrift() {
        return new TRpcClient();
    }
}
