package com.gsralex.gflow.executor.client.action;

/**
 * @author gsralex
 * @version 2019/2/8
 */
public abstract class BaseReq {

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
