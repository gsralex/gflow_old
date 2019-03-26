package com.gsralex.gflow.core.rpc.server;

import com.gsralex.gflow.core.rpc.protocol.RpcReq;
import com.gsralex.gflow.core.rpc.protocol.RpcResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcReq> {

    private static final Logger LOG = LoggerFactory.getLogger(RpcServerHandler.class);
    private ServiceCache serviceCache;

    public RpcServerHandler(ServiceCache serviceCache) {
        this.serviceCache = serviceCache;
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
        if (serviceCache.containsHandler(className)) {
            Object instance = serviceCache.getHandler(className);
            Method method = findMethod(className, rpcReq.getMethodName(), rpcReq.getParameters());
            return method.invoke(instance, rpcReq.getParameters());
        }
        return null;
    }


    private Method findMethod(String className, String methodName, Object[] parameters) throws NoSuchMethodException {
        List<Method> sameNameMethods = serviceCache.getMethods(className, methodName);
        for (Method method : sameNameMethods) {
            if (method.getParameterTypes().length == parameters.length) {
                if (isEqualsMethodParameters(method.getParameterTypes(), getParameterClass(parameters))) {
                    return method;
                }
            }
        }
        throw new NoSuchMethodException();
    }

    private boolean isEqualsMethodParameters(Class<?>[] serverParams, Class<?>[] remoteParams) {
        for (int i = 0; i < serverParams.length; i++) {
            Class<?> serverParam = serverParams[i];
            Class<?> remoteParam = remoteParams[i];
            if (serverParam.equals(remoteParam)) {
            } else {
                if (serverParam.equals(long.class) && remoteParam.equals(Long.class)) {
                } else if (serverParam.equals(boolean.class) && remoteParam.equals(Boolean.class)) {
                } else if (serverParam.equals(int.class) && remoteParam.equals(Integer.class)) {
                } else if (serverParam.equals(double.class) && remoteParam.equals(Double.class)) {
                } else if (serverParam.equals(short.class) && remoteParam.equals(Short.class)) {
                } else if (serverParam.equals(float.class) && remoteParam.equals(Short.class)) {
                } else if (serverParam.equals(byte.class) && remoteParam.equals(Byte.class)) {
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    private Class<?>[] getParameterClass(Object[] parameters) {
        Class<?>[] classArr = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            classArr[i] = parameters[i].getClass();
        }
        return classArr;
    }


    public void registerHandler(Class<?> clazz, Object impl) {
        serviceCache.registerHandler(clazz, impl);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("RpcServerHandler.exceptionCaught", cause);
        ctx.close();
    }

    public static class D {
        public void d(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d1(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d2(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d3(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d4(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d5(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d6(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d7(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d8(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d9(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d10(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d11(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d12(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d13(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d14(long d1, String d2, long d3) {
            System.out.println("d");
        }


        public void d(long d1, String d2, long d3, long d4) {
            System.out.println("d");
        }

    }

    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println(long.class);
        System.out.println(Long.class);

        ServiceCache serviceCache = new ServiceCache();
        RpcServerHandler handler = new RpcServerHandler(serviceCache);
        handler.registerHandler(D.class, new D());
        D d = new D();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            handler.findMethod(D.class.getName(), "d", new Object[]{1L, "d", 2L});
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
