package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thriftgen.TAckDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleAckService;
import com.gsralex.gflow.core.util.AccessTokenUtils;
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
        String accessKey = GFlowContext.getContext().getConfig().getAccessKey();
        TResult tResult = new TResult();
        if (!AccessTokenUtils.check(accessKey, desc.getAccessToken())) {
            tResult.setCode(ErrConstants.ERR_ACCESSTOKEN);
            tResult.setErrmsg(ErrConstants.MSG_ERRACCESTOKEN);
            return tResult;
        }
        boolean ok = desc.getCode() == ErrConstants.OK ? true : false;
        scheduleLinkHandle.ackAction(desc.getJobId(), ok);
        tResult.setCode(ErrConstants.OK);
        tResult.setErrmsg(ErrConstants.MSG_OK);
        return tResult;
    }
}
