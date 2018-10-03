package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleAckService;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2018/8/21
 */
@Service
public class TScheduleAckServiceImpl implements TScheduleAckService.Iface {

    @Autowired
    private ScheduleLinkHandle scheduleLinkHandle;

    @Override
    public TResult ack(long jobId, boolean ok) throws TException {
        scheduleLinkHandle.ackAction(jobId, ok);
        TResult tResult = new TResult();
        tResult.setOk(true);
        return tResult;
    }
}
