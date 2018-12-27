package com.gsralex.gflow.scheduler.executorclient;


import com.gsralex.gflow.core.thriftgen.TResp;

/**
 * @author gsralex
 * @version 2018/12/13
 */
public class RpcResult {

    private int retryCnt;

    private TResp tResult;

    private boolean ok;

    public RpcResult(int retryCnt, TResp tResult) {
        this.retryCnt = retryCnt;
        this.tResult = tResult;
    }

    public int getRetryCnt() {
        return retryCnt;
    }

    public TResp gettResult() {
        return tResult;
    }
}
