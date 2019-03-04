package com.gsralex.gflow.web.handles;

import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.client.ActionClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;

/**
 * @author gsralex
 * @version 2019/3/4
 */
public class ClientManager {

    private static ActionClient actionClient;

    static {
        actionClient = SchedulerClientFactory.createAction(new IpAddr("localhost", 20091), "");
    }


    public static ActionClient getActionClient() {
        return actionClient;
    }
}
