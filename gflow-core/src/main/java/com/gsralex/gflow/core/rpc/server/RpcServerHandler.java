package com.gsralex.gflow.core.rpc.server;

import com.gsralex.gflow.core.rpc.protocol.RpcReq;
import com.gsralex.gflow.core.rpc.protocol.RpcResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcReq> {

    private static final Logger LOG = LoggerFactory.getLogger(RpcServerHandler.class);
    private Map<String, Object> serviceHandles;

    public RpcServerHandler(Map<String, Object> serviceHandles) {
        this.serviceHandles = serviceHandles;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcReq req) {
        RpcResp resp = new RpcResp();
        resp.setReqId(req.getReqId());
        Object result = null;
        try {
            result = doAction(req);
        } catch (Exception e) {
            LOG.error("RpcServerHandler.channelRead0 reqId:" + req.getReqId(), e);
            resp.setMsg(e.getMessage());
        }
        resp.setData(result);
        ctx.writeAndFlush(resp);
    }


    public Object doAction(RpcReq rpcReq) throws Exception {
        String className = rpcReq.getClassName();
        if (serviceHandles.containsKey(className)) {
            Object instance = serviceHandles.get(className);
            Method method = instance.getClass().getMethod(rpcReq.getMethodName(), getParameterClass(rpcReq.getParameters()));
            return method.invoke(instance, rpcReq.getParameters());
        }
        return null;
    }

    private Class[] getParameterClass(Object[] parameters) {
        Class[] classArr = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            classArr[i] = parameters[i].getClass();
        }
        return classArr;
    }


    public void registerHandler(Class clazz, Object impl) {
        serviceHandles.put(clazz.getName(), impl);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("RpcServerHandler.exceptionCaught", cause);
        ctx.close();
    }
}
