package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.pub.thriftgen.scheduler.TScheduleService;
import org.apache.thrift.TException;


public interface ClientCallback<T> {


    T doAction(TScheduleService.Client client) throws TException;

}
