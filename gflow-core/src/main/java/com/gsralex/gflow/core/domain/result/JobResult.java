package com.gsralex.gflow.core.domain.result;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class JobResult {

    private boolean ok;
    private String errMsg;

    public JobResult(boolean ok, String errMsg) {
        this.ok = ok;
        this.errMsg = errMsg;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
