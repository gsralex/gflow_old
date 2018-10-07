package com.gsralex.scheduler.api;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.zk.SchedulerIpData;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/10/3
 */
public class SchedulerApiContext {

    private GFlowContext context;
    private SchedulerIpData ipData;


    private SchedulerApiContext() {
        context = GFlowContext.getContext();
        if (context.getConfig() == null) {
            context.initConfig();
            ipData = new SchedulerIpData(context);
        }

    }

    private static SchedulerApiContext currentContext = new SchedulerApiContext();


    public static SchedulerApiContext getContext() {
        return currentContext;
    }

    public GFlowContext getGFlowContext() {
        return context;
    }


    public List<IpAddress> getIps() {
        return ipData.getIps();
    }
}
