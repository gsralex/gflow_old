package com.gsralex.gflow.core.zk;

import com.gsralex.gflow.core.context.IpAddress;
import org.apache.commons.lang3.StringUtils;

/**
 * @author gsralex
 * @version 2018/9/27
 */
public class ZkUtils {

    public static IpAddress getIpByZkPath(String path) {
        //ip1_ip:port
        String[] paths = StringUtils.split(path, "_");
        String name = paths[0];
        String ipport = paths[1];
        return IpAddress.getIp(ipport);
    }

    public static String getZkPathByIp(int index, IpAddress address) {
        return index + "_" + address.toString();
    }
}
