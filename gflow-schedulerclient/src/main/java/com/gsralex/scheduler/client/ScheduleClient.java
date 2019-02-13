package com.gsralex.scheduler.client;

import com.gsralex.scheduler.client.action.scheduler.*;

public interface ScheduleClient {

    JobResp scheduleAction(JobReq req);

    ScheduleGroupResp scheduleGroup(ScheduleGroupReq req);

    GetJobGroupResp getJobGroup(long id);
}
