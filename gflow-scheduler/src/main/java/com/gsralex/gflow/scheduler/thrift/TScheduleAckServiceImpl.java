package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleAckService;
import com.gsralex.gflow.scheduler.FlowService;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/8/21
 */
public class TScheduleAckServiceImpl implements TScheduleAckService.Iface {

    private GFlowContext context;
    private FlowService flowService;

    public TScheduleAckServiceImpl(GFlowContext context) {
        this.context = context;
    }

    @Override
    public TResult ack(long jobId, boolean ok) throws TException {
        flowService.actionAck(jobId, ok);
        TResult tResult = new TResult();
        tResult.setOk(true);
        return tResult;
    }
}
