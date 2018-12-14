package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.zk.SchedulerIpData;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/10/3
 */
public class ScheduleClientContext {

    private GFlowContext context;
    private SchedulerIpData ipData;


    private ScheduleClientContext() {
        context = GFlowContext.getContext();
        if (context.getConfig() == null) {
            context.initConfig();
            ipData = new SchedulerIpData(context);
        }
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

    public static void main(String[] args) {
        int id = Integer.parseInt(args[0]);
        Parameter parameter = new Parameter();
        parameter.put("bizdate", args[1]);
        ScheduleClient scheduleClient = new ScheduleClientImpl();
        scheduleClient.scheduleGroup(id, parameter);
    }
}
