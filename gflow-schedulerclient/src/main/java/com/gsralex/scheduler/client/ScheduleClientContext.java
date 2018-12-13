package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.zk.SchedulerIpData;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/10/3
 */
public class ScheduleClientContext {

    private GFlowContext context;
    private SchedulerIpData ipData;
    private static final ScheduleClient scheduleClient = new ScheduleClientImpl();


    private ScheduleClientContext() {
        context = GFlowContext.getContext();
        if (context.getConfig() == null) {
            context.initConfig();
            ipData = new SchedulerIpData(context);
        }
    }

    public static ScheduleClient getClient() {
        return scheduleClient;
    }

    private static ScheduleClientContext currentContext = new ScheduleClientContext();


    public static ScheduleClientContext getContext() {
        return currentContext;
    }

    public GFlowContext getGFlowContext() {
        return context;
    }


    public List<IpAddress> getIps() {
        return ipData.getIps();
    }
}
