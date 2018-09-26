package com.gsralex.gflow.webmanager.model;

/**
 * @author gsralex
 * @version 2018/9/5
 */
public class ResultDto {

    private int code;
    private String msg;

    public ResultDto() {
        this(0, "");
    }

    public ResultDto(int code) {
        this(code, "");
    }

    public ResultDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
