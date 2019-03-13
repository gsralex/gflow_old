package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.scheduler.client.action.scheduler.*;

public interface SchedulerClient {

    JobResp scheduleAction(JobReq req);

    ScheduleGroupResp scheduleGroup(ScheduleGroupReq req);

    Resp ack(AckReq req);

    Resp ackMaster(AckReq req);

    NodeResp executorHb(ExecutorHbReq req);

    NodeResp schedulerHb(ScheduleHbReq req);

    NodeStatusResp listExecutorNode();

    NodeStatusResp listSchedulerNode();
}
