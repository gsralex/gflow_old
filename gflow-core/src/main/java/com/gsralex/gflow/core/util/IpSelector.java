package com.gsralex.gflow.core.util;

import com.gsralex.gflow.core.context.IpAddress;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/13
 */
public class IpSelector {

    private IpAddress ip;
    private List<IpAddress> ipList;
    private int seq;

    public IpSelector(IpAddress ip) {
        this.ip = ip;
    }

    public IpSelector(List<IpAddress> ipList) {
        this.ipList = ipList;
    }

    public IpAddress getIp() {
        if (ip != null) {
            return ip;
        }
        if (ipList != null && ipList.size() != 0) {
            if (seq >= ipList.size()) {
                seq = 0;
            }
            IpAddress ip = ipList.get(seq);
            seq++;
            return ip;
        }
        return null;
    }

}
