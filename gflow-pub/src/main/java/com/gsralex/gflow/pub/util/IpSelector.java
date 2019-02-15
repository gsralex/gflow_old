package com.gsralex.gflow.pub.util;

import com.gsralex.gflow.pub.context.IpAddr;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/13
 */
public class IpSelector {

    private IpAddr ip;
    private List<IpAddr> ipList;
    private int seq;

    public IpSelector(IpAddr ip) {
        this.ip = ip;
    }

    public IpSelector(List<IpAddr> ipList) {
        this.ipList = ipList;
    }

    public void setIp(List<IpAddr> ipList) {
        this.ipList = ipList;
    }

    public IpAddr getIp() {
        if (ip != null) {
            return ip;
        }
        synchronized (ipList) {
            if (ipList != null && ipList.size() != 0) {
                if (seq >= ipList.size()) {
                    seq = 0;
                }
                IpAddr ip = ipList.get(seq);
                seq++;
                return ip;
            }
        }
        return null;
    }

}
