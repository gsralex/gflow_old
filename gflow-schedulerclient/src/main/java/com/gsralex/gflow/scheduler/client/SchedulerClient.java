package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.pub.action.Req;
import com.gsralex.gflow.scheduler.client.action.scheduler.*;
import com.gsralex.gflow.pub.action.Resp;

public interface SchedulerClient {

    JobResp scheduleAction(JobReq req);

    ScheduleGroupResp scheduleGroup(ScheduleGroupReq req);

    Resp ack(AckReq req);

    Resp ackMaster(AckReq req);

    Resp executorHb(ExecutorHbReq req);

    Resp schedulerHb(ScheduleHbReq req);

    Resp updateExecutorNode(ExecutorHbReq req);

    NodeResp listSchedulerNode();
}
