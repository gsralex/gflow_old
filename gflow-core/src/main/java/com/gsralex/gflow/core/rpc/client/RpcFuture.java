package com.gsralex.gflow.core.rpc.client;

import com.gsralex.gflow.core.exception.GflowException;
import com.gsralex.gflow.core.rpc.protocol.RpcResp;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gsralex
 * @version 2019/3/17
 */
public class RpcFuture implements Future<Object> {

    private static final int TIMEOUT_SECONDS = 30;

    private boolean fin;
    private String reqId;
    private RpcResp rpcResp;
    private ReentrantLock lock = new ReentrantLock();
    private Condition done = lock.newCondition();


    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public void doReceived(RpcResp rpcResp){
        this.rpcResp=rpcResp;
        fin=true;
        done.signal();
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
        try {
            lock.lock();
            done.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } finally {
            lock.unlock();
        }
        if (!isDone()) {
            throw new GflowException("Timeout");
        }
        return rpcResp.getData();
    }

    @Override
    public Object get(long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
}
