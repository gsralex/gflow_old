package com.gsralex.gflow.scheduler.server;


import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.scheduler.*;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.hb.ExecutorNode;
import com.gsralex.gflow.scheduler.hb.HbService;
import com.gsralex.gflow.scheduler.schedule.ActionResult;
import com.gsralex.gflow.scheduler.schedule.FlowResult;
import com.gsralex.gflow.scheduler.service.SchedulerService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/3/18
 */
@Component
public class TScheduleServiceImpl implements TScheduleService.Iface {


    private SchedulerContext context = SchedulerContext.getInstance();
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private HbService hbService;


    private boolean getRetry() {
        return false;
    }

    @Override
    public TJobResp scheduleAction(TJobReq req) {
        TJobResp resp = new TJobResp();
//        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
//            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
//            return resp;
//        }
        ActionResult result = schedulerService.scheduleAction(req.getActionId(), req.getParameter(), getRetry());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        resp.setJobId(result.getJobId());
        return resp;
    }


    @Override
    public TScheduleGroupResp scheduleGroup(TGroupJobReq req) {
        TScheduleGroupResp resp = new TScheduleGroupResp();
//        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
//            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
////            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
//            return resp;
//        }
        FlowResult result = schedulerService.scheduleGroup(req.getGroupId(), req.getParameter(), 0, getRetry());
        resp.setCode(ErrConstants.OK);
        resp.setJobGroupId(result.getJobGroupId());

//        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp pauseGroup(TGroupJobReq req) {
        TResp resp = new TResp();
//        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
//            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
//            return resp;
//        }
        schedulerService.pauseGroup(req.getGroupId());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp stopGroup(TGroupJobReq req) {
        TResp resp = new TResp();
//        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
//            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
//            return resp;
//        }
        schedulerService.stopGroup(req.getGroupId());
        resp.setCode(ErrConstants.OK);
        resp.setMsg(ErrConstants.MSG_OK);
        return resp;
    }

    @Override
    public TResp ack(TAckReq req) throws TException {
        TResp resp = new TResp();
//        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
//            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
//            return resp;
//        }
        boolean ok = schedulerService.ackAction(req.getJobId(), req.getCode(), req.getMsg(), getRetry());
        resp.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        return resp;
    }

    @Override
    public TResp ackMaster(TAckReq req) throws TException {
        TResp resp = new TResp();
//        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
//            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
//            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
//            return resp;
//        }
        boolean ok = schedulerService.ackMaster(req.getJobId(), req.getCode(), req.getMsg(), getRetry());
        resp.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        return resp;
    }

    @Override
    public TSchedulerNodeResp executorHb(TExecutorHbReq req) throws TException {
        List<IpAddr> ipList = hbService.executorHb(new IpAddr(req.getIp(), req.getPort()), req.getTag());
        TSchedulerNodeResp resp = new TSchedulerNodeResp();
        return resp;
    }

    @Override
    public TExecutorNodeResp schedulerHb(TScheduleHbReq req) throws TException {
        List<ExecutorNode> list = hbService.schedulerHb(new IpAddr(req.getIp(), req.getPort()));
        TExecutorNodeResp resp = new TExecutorNodeResp();
        return resp;
    }


    @Override
    public String serverStatus() throws TException {
        if (context.isMaster()) {
            return "master";
        } else {
            return "slave";
        }
    }

}
