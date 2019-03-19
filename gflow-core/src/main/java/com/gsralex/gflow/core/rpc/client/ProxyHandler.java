package com.gsralex.gflow.core.rpc.client;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.protocol.RpcReq;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class ProxyHandler implements InvocationHandler {
    private Class clazz;
    private RpcClientManager rpcClientManager;
    private IpAddr ip;

    public ProxyHandler(Class clazz, RpcClientManager rpcClientManager) {
        this.clazz = clazz;
        this.rpcClientManager = rpcClientManager;
    }

    public ProxyHandler(Class clazz, RpcClientManager rpcClientManager, IpAddr ip) {
        this.clazz = clazz;
        this.rpcClientManager = rpcClientManager;
        this.ip = ip;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        RpcReq req = new RpcReq();
        String reqId = UUID.randomUUID().toString();
        req.setReqId(reqId);
        req.setParameters(args);
        req.setClassName(clazz.getName());
        req.setMethodName(method.getName());
        req.setParameters(args);
        RpcClientHandler rpcClientHandler;
        if (ip != null) {
            rpcClientHandler = rpcClientManager.getRpcClientHandler(ip);
        } else {
            rpcClientHandler = rpcClientManager.getRpcClientHandler();
        }
        RpcFuture future = rpcClientHandler.sendReq(req);
        return future.get();
    }
}
