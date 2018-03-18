package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.scheduler.thrift.gen.TGroupJobDesc;
import com.gsralex.gflow.scheduler.thrift.gen.TJobDesc;
import com.gsralex.gflow.scheduler.thrift.gen.TJobResult;
import com.gsralex.gflow.scheduler.thrift.gen.TScheduleService;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TScheduleServiceImpl implements TScheduleService.Iface {
    @Override
    public TJobResult submit(TJobDesc desc) throws TException {

        return null;
    }

    @Override
    public TJobResult submitGroup(TGroupJobDesc desc) throws TException {
        return null;
    }

    @Override
    public TJobResult schedule(TJobDesc desc) throws TException {
        return null;
    }
}
