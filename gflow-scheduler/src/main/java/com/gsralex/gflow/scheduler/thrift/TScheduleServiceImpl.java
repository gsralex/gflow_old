package com.gsralex.gflow.scheduler.thrift;


import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.TGroupJobDesc;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2018/3/18
 */
@Service
public class TScheduleServiceImpl implements TScheduleService.Iface {


    @Autowired
    private ScheduleLinkHandle scheduleLinkHandle;


    @Override
    public TResult schedule(TJobDesc desc) throws TException {
        scheduleLinkHandle.scheduleAction(desc.getActionId(), new Parameter(desc.getParameter()));
        TResult tResult = new TResult();
        tResult.setOk(true);
        return tResult;
    }


    @Override
    public TResult scheduleGroup(TGroupJobDesc desc) throws TException {
        scheduleLinkHandle.scheduleGroup(desc.getGroupId(), new Parameter(desc.getParameter()), 0);
        TResult tResult = new TResult();
        tResult.setOk(true);
        return tResult;
    }
}
