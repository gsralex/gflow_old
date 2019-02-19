package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.context.IpAddr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/2/18
 */
public class ExecutorIpSelector {
    private Map<String, Integer> seqMap = new HashMap<>();

    public IpAddr getTagIpSeq(String tag, List<IpAddr> ipList) {
        if (!seqMap.containsKey(tag)) {
            seqMap.put(tag, 0);
            return ipList.get(0);
        } else {
            int seq = seqMap.get(tag);
            seq++;
            if (seq >= ipList.size()) {
                seq = 0;
            }
            return ipList.get(seq);
        }
    }

}
