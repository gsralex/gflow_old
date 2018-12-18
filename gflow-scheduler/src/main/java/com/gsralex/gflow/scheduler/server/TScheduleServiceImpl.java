package com.gsralex.gflow.scheduler.server;


import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.scheduler.TAckReq;
import com.gsralex.gflow.core.thriftgen.scheduler.TGroupJobReq;
import com.gsralex.gflow.core.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.core.thriftgen.scheduler.TScheduleService;
import com.gsralex.gflow.scheduler.SchedulerContext;
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
        chain = new ScheduleChain(context.getConfig().getAccessKey());
        scheduleLinkHandle = new ScheduleLinkHandle(context);
    }

    @Override
    public TResp scheduleAction(TJobReq req) {
        class ScheduleActionCallback implements ScheduleCallback {
            @Override
            public TResp doSchedule() {
                TResp tResult = new TResp();
                scheduleLinkHandle.scheduleAction(req.getActionId(), req.getParameter());
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return chain.execute(new ScheduleActionCallback(), req.getAccessToken());
    }

    @Override
    public TResp scheduleGroup(TGroupJobReq req) {
        class ScheduleGroupCallback implements ScheduleCallback {
            @Override
            public TResp doSchedule() {
                scheduleLinkHandle.scheduleGroup(req.getGroupId(), req.getParameter(), 0);
                TResp tResult = new TResp();
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return chain.execute(new ScheduleGroupCallback(), req.getAccessToken());
    }

    @Override
    public TResp pauseGroup(TGroupJobReq req) {
        class PauseGroupCallback implements ScheduleCallback {
            @Override
            public TResp doSchedule() {
                scheduleLinkHandle.pauseGroup(req.getGroupId());
                TResp tResult = new TResp();
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return chain.execute(new PauseGroupCallback(), req.getAccessToken());
    }

    @Override
    public TResp stopGroup(TGroupJobReq req) {
        class StopGroupCallback implements ScheduleCallback {
            @Override
            public TResp doSchedule() {
                scheduleLinkHandle.stopGroup(req.getGroupId());
                TResp tResult = new TResp();
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return chain.execute(new StopGroupCallback(), req.getAccessToken());
    }

    @Override
    public TResp ack(TAckReq req) throws TException {
        class AckCallback implements ScheduleCallback {
            @Override
            public TResp doSchedule() {
                boolean ok = req.getCode() == ErrConstants.OK ? true : false;
                scheduleLinkHandle.ackAction(req.getJobId(), ok);
                TResp tResult = new TResp();
                tResult.setCode(ErrConstants.OK);
                tResult.setMsg(ErrConstants.MSG_OK);
                return tResult;
            }
        }
        return chain.execute(new AckCallback(), req.getAccessToken());
    }


}
