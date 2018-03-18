package com.gsralex.gflow.core.api;

/**
 * @author gsralex
 * @date 2018/2/12
 */
public class FlowResult {

    private boolean ok;
    private String errMsg;

    public FlowResult(boolean ok, String errMsg) {
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
