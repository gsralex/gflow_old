package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.thrift.TJobDesc;
import com.gsralex.gflow.core.thrift.TJobGroupDesc;
import com.gsralex.gflow.core.thrift.TJobResult;
import com.gsralex.gflow.core.thrift.TScheduleService;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @date 2018/2/18
 */
public class TScheduleServiceImpl implements TScheduleService.Iface {
    @Override
    public TJobResult schedule(TJobDesc desc) throws TException {
        return null;
    }

    @Override
    public TJobResult scheduleGroup(TJobGroupDesc desc) throws TException {
        return null;
    }
}
