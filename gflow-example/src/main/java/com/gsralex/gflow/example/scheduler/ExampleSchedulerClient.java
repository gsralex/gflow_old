package com.gsralex.gflow.example.scheduler;

import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.action.scheduler.ScheduleGroupReq;
import com.gsralex.gflow.scheduler.client.impl.SchedulerClientImpl;

/**
 * @author gsralex
 * @version 2019/3/14
 */
public class ExampleSchedulerClient {

    public static void main(String[] args) {
        SchedulerClient client = new SchedulerClientImpl(new IpAddr("127.0.0.1:20091"), "");
        ScheduleGroupReq req = new ScheduleGroupReq();
        req.setFlowGroupId(1);
        req.setParameter("bizdate=20190313");
        client.scheduleGroup(req);
    }
}
