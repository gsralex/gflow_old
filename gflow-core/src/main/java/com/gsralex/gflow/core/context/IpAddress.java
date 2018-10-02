package com.gsralex.gflow.core.context;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class IpAddress {

    private String ip;
    private int port;

    public IpAddress(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

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

    public static IpAddress getIp(String ip) {
        String[] ipport = StringUtils.split(ip, ":");
        if (ipport.length < 2) {
            //TODO:加入exception
        }

        String ipAddress = ipport[0];
        int port = 0;
        try {
            port = Integer.parseInt(ipport[1]);
        } catch (NumberFormatException e) {
            //TODO:加入exception
        }
        return new IpAddress(ipAddress, port);
    }

    public static List<IpAddress> getIpsByConfig(String ipStr) {
        List<IpAddress> ipList = new ArrayList<>();
        if (!StringUtils.isEmpty(ipStr)) {
            String[] ips = StringUtils.split(ipStr, ",");
            for (String ip : ips) {
                ipList.add(IpAddress.getIp(ip));
            }
        }
        return ipList;
    }

    @Override
    public String toString() {
        return ip + ":" + port;
    }
}
