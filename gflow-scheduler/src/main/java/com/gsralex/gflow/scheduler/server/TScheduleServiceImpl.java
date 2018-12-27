package com.gsralex.gflow.scheduler.server;


import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.scheduler.*;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.domain.JobGroup;
import com.gsralex.gflow.scheduler.schedule.ActionResult;
import com.gsralex.gflow.scheduler.schedule.FlowResult;
import com.gsralex.gflow.scheduler.schedule.SchedulerService;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TScheduleServiceImpl implements TScheduleService.Iface {


    private SchedulerService schedulerService;
    private String accessKey;

    public TScheduleServiceImpl(SchedulerContext context) {
        accessKey = context.getConfig().getAccessKey();
        schedulerService = new SchedulerService(context);
    }


    private boolean getRetry() {
        return false;
    }

    @Override
    public TJobResp scheduleAction(TJobReq req) {
        TJobResp resp = new TJobResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        ActionResult result = schedulerService.scheduleAction(req.getActionId(), req.getParameter(), getRetry());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        resp.setJobId(result.getJobId());
        return resp;
    }

    @Override
    public TScheduleGroupResp scheduleGroup(TGroupJobReq req) {
        TScheduleGroupResp resp = new TScheduleGroupResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        FlowResult result = schedulerService.scheduleGroup(req.getGroupId(), req.getParameter(), 0, getRetry());
        resp.setCode(ErrConstants.OK);
        resp.setJobGroupId(result.getJobGroupId());

//        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp pauseGroup(TGroupJobReq req) {
        TResp resp = new TResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        schedulerService.pauseGroup(req.getGroupId());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp stopGroup(TGroupJobReq req) {
        TResp resp = new TResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        schedulerService.stopGroup(req.getGroupId());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp ack(TAckReq req) throws TException {
        TResp resp = new TResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        boolean ok = req.getCode() == ErrConstants.OK ? true : false;
        schedulerService.ackAction(req.getJobId(), ok, getRetry());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TGetJobGroupResp getGroup(TGetJobGroupReq req) throws TException {
        TGetJobGroupResp resp = new TGetJobGroupResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        JobGroup jobGroup = schedulerService.getJobGroup(req.getId());
        if (jobGroup != null) {
            TJobGroup tJobGroup = new TJobGroup();
            tJobGroup.setId(jobGroup.getId());
            tJobGroup.setStatus(GroupStatus.findByValue(jobGroup.getStatus()));
            resp.setJobGroup(tJobGroup);
        }
        return resp;
    }

    @Override
    public TGetJobResp getJob(TGetJobReq req) throws TException {
        return null;
    }


}
