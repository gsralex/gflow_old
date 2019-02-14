package com.gsralex.gflow.scheduler.client.action.scheduler;

import com.gsralex.gflow.core.action.Req;

/**
 * @author gsralex
 * @version 2019/2/13
 */
public class ExecutorHbReq extends Req {

//    1:string ip
//    2:i32 port
//    3:string tag
//    4:string accessToken;

    private String ip;
    private int port;
    private String tag;
    private boolean online;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
