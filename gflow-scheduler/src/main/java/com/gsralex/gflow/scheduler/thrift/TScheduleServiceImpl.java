package com.gsralex.gflow.scheduler.thrift;


import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thriftgen.TGroupJobDesc;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import com.gsralex.gflow.core.util.AccessTokenUtils;
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
        String accessKey = GFlowContext.getContext().getConfig().getAccessKey();
        TResult tResult = new TResult();
        if (!AccessTokenUtils.check(accessKey, desc.getAccessToken())) {
            tResult.setCode(ErrConstants.ERR_ACCESSTOKEN);
            tResult.setErrmsg(ErrConstants.MSG_ERRACCESTOKEN);
            return tResult;
        }
        scheduleLinkHandle.scheduleAction(desc.getActionId(), desc.getParameter());
        tResult.setCode(ErrConstants.OK);
        tResult.setErrmsg(ErrConstants.MSG_OK);
        return tResult;
    }

    @Override
    public TResult scheduleGroup(TGroupJobDesc desc) throws TException {
        String accessKey = GFlowContext.getContext().getConfig().getAccessKey();
        TResult tResult = new TResult();
        if (!AccessTokenUtils.check(accessKey, desc.getAccessToken())) {
            tResult.setCode(ErrConstants.ERR_ACCESSTOKEN);
            tResult.setErrmsg(ErrConstants.MSG_ERRACCESTOKEN);
            return tResult;
        }
        scheduleLinkHandle.scheduleGroup(desc.getGroupId(), desc.getParameter(), 0);
        tResult.setCode(ErrConstants.OK);
        tResult.setErrmsg(ErrConstants.MSG_OK);
        return tResult;
    }
}
