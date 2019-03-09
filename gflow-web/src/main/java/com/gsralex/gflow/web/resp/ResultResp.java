package com.gsralex.gflow.web.resp;

import com.gsralex.gflow.pub.constants.ErrConstants;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public class ResultResp {

    private int code;
    private String msg;
    private Object data;


    public ResultResp() {

    }

    public ResultResp(int code) {
        this.code = code;
    }

    public ResultResp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultResp wrapOk(boolean ok) {
        ResultResp resp = new ResultResp();
        resp.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        return resp;
    }

    public static ResultResp wrapData(Object data) {
        ResultResp resp = new ResultResp();
        resp.setData(data);
        return resp;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
