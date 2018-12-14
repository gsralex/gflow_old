package com.gsralex.gflow.scheduler.server;


import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thriftgen.*;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TScheduleServiceImpl implements TScheduleService.Iface {

    private ScheduleLinkHandle scheduleLinkHandle;

    public TScheduleServiceImpl(SchedulerContext context) {
        scheduleLinkHandle = new ScheduleLinkHandle(context);
    }

    @Override
    public TResult scheduleAction(TJobDesc desc) throws TException {
        class ScheduleActionCallback implements ScheduleCallback {
            @Override
            public TResult doSchedule() {
                TResult tResult = new TResult();
                scheduleLinkHandle.scheduleAction(desc.getActionId(), desc.getParameter());
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return execute(new ScheduleActionCallback(), desc.getAccessToken());
    }

    @Override
    public TResult scheduleGroup(TGroupJobDesc desc) throws TException {
        class ScheduleGroupCallback implements ScheduleCallback {
            @Override
            public TResult doSchedule() {
                scheduleLinkHandle.scheduleGroup(desc.getGroupId(), desc.getParameter(), 0);
                TResult tResult = new TResult();
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return execute(new ScheduleGroupCallback(), desc.getAccessToken());
    }

    @Override
    public TResult pauseGroup(TGroupJobDesc desc) throws TException {
        class PauseGroupCallback implements ScheduleCallback {
            @Override
            public TResult doSchedule() {
                scheduleLinkHandle.pauseGroup(desc.getGroupId());
                TResult tResult = new TResult();
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return execute(new PauseGroupCallback(), desc.getAccessToken());
    }

    @Override
    public TResult stopGroup(TGroupJobDesc desc) throws TException {
        class StopGroupCallback implements ScheduleCallback {
            @Override
            public TResult doSchedule() {
                scheduleLinkHandle.stopGroup(desc.getGroupId());
                TResult tResult = new TResult();
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return execute(new StopGroupCallback(), desc.getAccessToken());
    }

    @Override
    public TResult setSettings(TSettingsDesc desc) throws TException {
        //TODO:空实现
        return null;
    }


    private TResult execute(ScheduleCallback callback, String accessToken) {
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
