package com.gsralex.gflow.pub.context;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author gsralex
 * @version 2019/2/19
 */
public class IpSeqSelector {

    public List<IpAddr> ipList;
    private Set<IpAddr> ipSet;
    private int seq;

    public IpSeqSelector(List<IpAddr> ipList) {
        this.ipList = ipList;
        this.ipSet = getSet(ipList);
    }

    private Set<IpAddr> getSet(List<IpAddr> ipList) {
        Set<IpAddr> ipSet = new HashSet<>();
        for (IpAddr ip : ipList) {
            ipSet.add(ip);
        }
        return ipSet;
    }

    public boolean setIpList(List<IpAddr> ipList) {
        boolean needUpdate = false;
        if (ipList.size() != this.ipList.size()) {
            needUpdate = true;
        }
        for (IpAddr ip : ipList) {
            if (!ipSet.contains(ip)) {
                needUpdate = true;
            }
        }
        if (needUpdate) {
            synchronized (ipList) {
                this.ipList = ipList;
                this.ipSet = getSet(ipList);
            }
        }
        return needUpdate;

    }


    public IpAddr getIp() {
        synchronized (ipList) {
            if (seq >= ipList.size()) {
                seq = 0;
            }
            IpAddr r = ipList.get(seq);
            seq++;
            return r;
        }
    }
}
