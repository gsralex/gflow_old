package com.gsralex.gflow.scheduler.client.action.scheduler;

import com.gsralex.gflow.pub.action.Req;

/**
 * @author gsralex
 * @version 2019/2/13
 */
public class AckReq extends Req {


    private long jobId;
    private int code;
    private String msg;
    private boolean masterProcess;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
