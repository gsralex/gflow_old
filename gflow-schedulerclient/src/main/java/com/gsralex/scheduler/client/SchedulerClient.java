package com.gsralex.scheduler.client;

import com.gsralex.gflow.executor.client.action.Resp;
import com.gsralex.scheduler.client.action.scheduler.*;

public interface SchedulerClient {

    JobResp scheduleAction(JobReq req);

    ScheduleGroupResp scheduleGroup(ScheduleGroupReq req);

    GetJobGroupResp getJobGroup(long id);

    Resp ack(AckReq req);

    Resp ackMaster(AckReq req);

    Resp executorHb(ExecutorHbReq req);

    Resp schedulerHb(ScheduleHbReq req);

    Resp updateExecutorNode(ExecutorHbReq req);
}
