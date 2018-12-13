package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.model.Result;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import org.apache.thrift.TException;


public interface ClientCallback {


    Result doSchedule(TScheduleService.Client client) throws TException;

}
