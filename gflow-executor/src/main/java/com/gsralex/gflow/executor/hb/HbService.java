package com.gsralex.gflow.executor.hb;

import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.NodeResp;

/**
 * @author gsralex
 * @version 2019/2/19
 */
public class HbService {

    private ExecutorContext context;

    public HbService(ExecutorContext context) {
        this.context = context;
    }

    public void listSchedulerNode(IpAddr askIp) {
        SchedulerClient client = SchedulerClientFactory.create(askIp, context.getAccessToken());
        NodeResp resp = client.listSchedulerNode();
        resp.getNodeList();
        for (IpAddr ip : resp.getNodeList()) {
            context.getSchedulerHbProcess().update(ip, true);
        }
    }

    public void updateSchedulerNode(IpAddr ip, boolean online) {
        context.getSchedulerHbProcess().update(ip, online);
    }

}
