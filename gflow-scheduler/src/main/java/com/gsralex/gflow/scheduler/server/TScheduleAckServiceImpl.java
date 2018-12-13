package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thriftgen.TAckDesc;
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
    public TResult ack(TAckDesc desc) throws TException {
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
        return execute(new AckCallback(), desc.getAccessToken());
    }

    public TResult execute(ScheduleCallback callback, String accessToken) {
        String accessKey = GFlowContext.getContext().getConfig().getAccessKey();
        TResult tResult = new TResult();
        if (!SecurityUtils.check(accessKey, accessToken)) {
            tResult.setCode(ErrConstants.ERR_ACCESSTOKEN);
            tResult.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return tResult;
        }
        return callback.doSchedule();
    }
}
