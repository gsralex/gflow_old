package com.gsralex.gflow.core.rpc.client;

import com.gsralex.gflow.core.context.IpAddr;

import java.lang.reflect.Proxy;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcClientFactory {

    public static <T> T create(Class<T> clazz, RpcClientManager clientManager) {
        Object instance = Proxy.newProxyInstance(RpcClientFactory.class.getClassLoader(), new Class[]{
                clazz
        }, new ProxyHandler(clazz, clientManager));
        return (T) instance;
    }

    public static <T> T create(Class<T> clazz, RpcClientManager clientManager, IpAddr ip) {
        Object instance = Proxy.newProxyInstance(RpcClientFactory.class.getClassLoader(), new Class[]{
                clazz
        }, new ProxyHandler(clazz, clientManager, ip));
        return (T) instance;
    }
}
