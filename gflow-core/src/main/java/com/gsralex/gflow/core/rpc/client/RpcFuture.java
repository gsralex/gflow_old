package com.gsralex.gflow.core.rpc.client;

import com.gsralex.gflow.core.rpc.protocol.RpcResp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author gsralex
 * @version 2019/3/17
 */
public class RpcFuture implements Future<Object> {

    private boolean fin;
    private String reqId;
    private RpcResp rpcResp;
    private CountDownLatch latch = new CountDownLatch(1);

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }


    public void setRpcResp(RpcResp rpcResp) {
        this.rpcResp = rpcResp;
    }

    public void done() {
        fin = true;
        latch.countDown();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDone() {
        return fin;
    }

    @Override
    public Object get() throws InterruptedException {
        latch.await();
        return rpcResp.getData();
    }

    @Override
    public Object get(long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
}
