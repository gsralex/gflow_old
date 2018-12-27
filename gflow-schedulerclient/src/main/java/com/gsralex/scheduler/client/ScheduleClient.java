package com.gsralex.scheduler.client;

import com.gsralex.scheduler.client.action.scheduler.GetJobGroupResp;
import com.gsralex.scheduler.client.action.scheduler.ScheduleGroupReq;
import com.gsralex.scheduler.client.action.scheduler.ScheduleGroupResp;

public interface ScheduleClient {

    ScheduleGroupResp scheduleGroup(ScheduleGroupReq req);

    GetJobGroupResp getJobGroup(long id);
}
