package com.gsralex.gflow.executor.client;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.executor.client.impl.ExecutorClientImpl;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/8
 */
public class ExecutorClientFactory {

    public static ExecutorClient create(IpAddress ip) {
        return new ExecutorClientImpl(ip);
    }

    public static ExecutorClient create(List<IpAddress> ipList) {
        return new ExecutorClientImpl(ipList);
    }
}
