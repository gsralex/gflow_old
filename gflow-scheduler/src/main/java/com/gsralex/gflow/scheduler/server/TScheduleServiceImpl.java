package com.gsralex.gflow.scheduler.server;


import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.thriftgen.TReq;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.scheduler.*;
import com.gsralex.gflow.pub.util.SecurityUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.NodeResp;
import com.gsralex.gflow.scheduler.hb.HbService;
import com.gsralex.gflow.scheduler.schedule.ActionResult;
import com.gsralex.gflow.scheduler.schedule.FlowResult;
import com.gsralex.gflow.scheduler.schedule.SchedulerService;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TScheduleServiceImpl implements TScheduleService.Iface {


    private SchedulerContext context;
    private SchedulerService schedulerService;
    private HbService hbService;
    private String accessKey;

    public TScheduleServiceImpl(SchedulerContext context) {
        this.context = context;
        accessKey = context.getConfig().getAccessKey();
        schedulerService = new SchedulerService(context);
        hbService = new HbService(context);
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
        boolean ok = schedulerService.ackAction(req.getJobId(), req.getCode(), req.getMsg(), getRetry());
        resp.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        return resp;
    }

    @Override
    public TResp ackMaster(TAckReq req) throws TException {
        TResp resp = new TResp();
        if (!SecurityUtils.check(accessKey, req.getAccessToken())) {
            resp.setCode(ErrConstants.ERR_ACCESSTOKEN);
            resp.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return resp;
        }
        boolean ok = schedulerService.ackMaster(req.getJobId(), req.getCode(), req.getMsg(), getRetry());
        resp.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        return resp;
    }

    @Override
    public TResp executorHb(TExecutorHbReq req) throws TException {
        boolean ok = hbService.executorHb(new IpAddr(req.getIp(), req.getPort()), req.getTag());
        TResp resp = new TResp();
        resp.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        return resp;
    }

    @Override
    public TResp schedulerHb(TScheduleHbReq req) throws TException {
        boolean ok = hbService.schedulerHb(new IpAddr(req.getIp(), req.getPort()));
        TResp resp = new TResp();
        resp.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        return resp;
    }

    @Override
    public TResp updateExecutorNode(TExecutorHbReq req) throws TException {
        boolean ok = hbService.updateExecutorNode(new IpAddr(req.getIp(), req.getPort()), req.getTag(), req.isOnline());
        TResp resp = new TResp();
        resp.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        return resp;
    }

    @Override
    public TNodeResp listSchedulerNode(TReq req) throws TException {
        if (context.isMaster()) {
            TNodeResp tNodeResp = new TNodeResp();
            List<IpAddr> ipList = context.getHbContext().getmSchedulerHbProcess().listOnlineSlaveIp();
            tNodeResp.setNodeList(convertTNodeToIp(ipList));
            return tNodeResp;
        } else {
            SchedulerClient client = SchedulerClientFactory.createScheduler(context.getMasterIp(), context.getAccessToken());
            NodeResp nodeResp = client.listSchedulerNode();
            TNodeResp tNodeResp = new TNodeResp();
            tNodeResp.setCode(nodeResp.getCode());
            tNodeResp.setMsg(nodeResp.getMsg());
            List<IpAddr> ipList = nodeResp.getNodeList();
            tNodeResp.setNodeList(convertTNodeToIp(ipList));
            return tNodeResp;
        }
    }

    private List<TNode> convertTNodeToIp(List<IpAddr> ipList) {
        List<TNode> tNodeList = new ArrayList<>();
        for (IpAddr ip : ipList) {
            TNode tNode = new TNode();
            tNode.setIp(ip.getIp());
            tNode.setPort(ip.getPort());
            tNodeList.add(tNode);
        }
        return tNodeList;
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
