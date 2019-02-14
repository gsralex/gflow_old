package com.gsralex.gflow.executor.client;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.executor.client.impl.ExecutorClientImpl;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/8
 */
public class ExecutorClientFactory {

    public static ExecutorClient create(IpAddr ip, String accessToken) {
        return new ExecutorClientImpl(ip, accessToken);
    }

    public static ExecutorClient create(List<IpAddr> ipList, String accessToken) {
        return new ExecutorClientImpl(ipList, accessToken);
    }
}
