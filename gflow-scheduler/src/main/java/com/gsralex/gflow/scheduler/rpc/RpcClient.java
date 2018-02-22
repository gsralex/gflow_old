package com.gsralex.gflow.scheduler.rpc;

/**
 * @author gsralex
 * @date 2018/2/18
 */
public interface RpcClient {


    RpcResult send(String ip, int port, long actionId, String parameter);


}
