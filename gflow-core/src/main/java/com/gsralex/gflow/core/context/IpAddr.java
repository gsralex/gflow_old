package com.gsralex.gflow.core.context;


import java.util.ArrayList;
import java.util.List;

public class IpAddr {

    private String ip;
    private int port;

    public IpAddr(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public IpAddr(String ipPort) {
        String[] ipArr = ipPort.split(":");
        this.ip = ipArr[0];
        this.port = Integer.parseInt(ipArr[1]);
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

    public static IpAddr getIp(String ip) {
        String[] ipport = ip.split(":");
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
        return new IpAddr(ipAddress, port);
    }

    public static List<IpAddr> getIpsByConfig(String ipStr) {
        List<IpAddr> ipList = new ArrayList<>();
        if (ipStr != null) {
            String[] ips = ipStr.split(",");
            for (String ip : ips) {
                ipList.add(IpAddr.getIp(ip));
            }
        }
        return ipList;
    }

    @Override
    public String toString() {
        return ip + ":" + port;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof IpAddr) {
            IpAddr ip = (IpAddr) obj;
            if (ip.getIp().equals(this.ip) && ip.getPort() == this.port) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.ip.hashCode() ^ this.port;
    }
}
