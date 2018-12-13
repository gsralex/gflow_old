package com.gsralex.gflow.scheduler.scheduleclient;

import com.gsralex.gflow.core.thriftgen.TResult;

/**
 * @author gsralex
 * @version 2018/12/13
 */
public class RpcResult {

    private int retryCnt;

    private TResult tResult;

    private boolean ok;

    public RpcResult(int retryCnt, TResult tResult) {
        this.retryCnt = retryCnt;
        this.tResult = tResult;
    }

    public int getRetryCnt() {
        return retryCnt;
    }

    public TResult gettResult() {
        return tResult;
    }
}
