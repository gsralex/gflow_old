package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.model.Result;
import com.gsralex.gflow.core.thriftgen.TScheduleService;


public interface ScheduleCallback {


    Result doSchedule(TScheduleService.Client client);

}
