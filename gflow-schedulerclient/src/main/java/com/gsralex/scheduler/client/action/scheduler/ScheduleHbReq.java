package com.gsralex.scheduler.client.action.scheduler;

import com.gsralex.scheduler.client.action.Req;

/**
 * @author gsralex
 * @version 2019/2/14
 */
public class ScheduleHbReq extends Req {

//    1:string ip
//    2:i32 port
//    3:string accessToken

    private String ip;
    private int port;

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
}
