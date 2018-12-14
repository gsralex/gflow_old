package com.gsralex.gflow.scheduler.server;


import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.thriftgen.*;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.config.SchedulerConfig;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TScheduleServiceImpl implements TScheduleService.Iface {

    private ScheduleLinkHandle scheduleLinkHandle;

    private ScheduleChain chain;

    public TScheduleServiceImpl(SchedulerContext context) {
        chain=new ScheduleChain(context.getConfig().getAccessKey());
        scheduleLinkHandle = new ScheduleLinkHandle(context);
    }

    @Override
    public TResult scheduleAction(TJobDesc desc) {
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
        return chain.execute(new ScheduleActionCallback(), desc.getAccessToken());
    }

    @Override
    public TResult scheduleGroup(TGroupJobDesc desc) {
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
        return chain.execute(new ScheduleGroupCallback(), desc.getAccessToken());
    }

    @Override
    public TResult pauseGroup(TGroupJobDesc desc)  {
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
        return chain.execute(new PauseGroupCallback(), desc.getAccessToken());
    }

    @Override
    public TResult stopGroup(TGroupJobDesc desc) {
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
        return chain.execute(new StopGroupCallback(), desc.getAccessToken());
    }

    @Override
    public TResult setSettings(TSettingsDesc desc) {
        //TODO:空实现
        return null;
    }

}
