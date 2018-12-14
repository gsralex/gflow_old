package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.thriftgen.TAckDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleAckService;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/8/21
 */
public class TScheduleAckServiceImpl implements TScheduleAckService.Iface {

    private ScheduleLinkHandle scheduleLinkHandle;
    private ScheduleChain chain;

    public TScheduleAckServiceImpl(SchedulerContext context) {
        scheduleLinkHandle = new ScheduleLinkHandle(context);
        chain=new ScheduleChain(context.getConfig().getAccessKey());
    }

    public TResult ack(TAckDesc desc)  {
        class AckCallback implements ScheduleCallback {
            @Override
            public TResult doSchedule() {
                boolean ok = desc.getCode() == ErrConstants.OK ? true : false;
                scheduleLinkHandle.ackAction(desc.getJobId(), ok);
                TResult tResult = new TResult();
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return chain.execute(new AckCallback(), desc.getAccessToken());
    }

}
