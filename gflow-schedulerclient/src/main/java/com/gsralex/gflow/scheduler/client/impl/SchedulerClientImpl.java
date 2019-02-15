package com.gsralex.gflow.scheduler.client.impl;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.scheduler.*;
import com.gsralex.gflow.core.util.IpSelector;
import com.gsralex.gflow.scheduler.client.ClientCallback;
import com.gsralex.gflow.scheduler.client.ClientWrapper;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.core.action.Resp;
import com.gsralex.gflow.scheduler.client.action.scheduler.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class SchedulerClientImpl implements SchedulerClient {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerClientImpl.class);

    private String accessToken;
    private IpSelector ipSelector;

    public SchedulerClientImpl(IpAddr ip, String accessToken) {
        this.ipSelector = new IpSelector(ip);
        this.accessToken = accessToken;
    }

    public SchedulerClientImpl(List<IpAddr> ipList, String accessToken) {
        this.ipSelector = new IpSelector(ipList);
        this.accessToken = accessToken;
    }

    @Override
    public JobResp scheduleAction(JobReq req) {
        class Callback implements ClientCallback<JobResp> {
            @Override
            public JobResp doAction(TScheduleService.Client client) throws TException {
                TJobReq tReq = new TJobReq();
                tReq.setJobGroupId(req.getJobGroupId());
                tReq.setIndex(req.getIndex());
                tReq.setParameter(req.getParameter());
                tReq.setClassName(req.getClassName());
                tReq.setActionId(req.getActionId());
                tReq.setAccessToken(accessToken);
                tReq.setId(req.getId());
                TJobResp tResp = client.scheduleAction(tReq);
                JobResp resp = new JobResp();
                resp.setJobId(tResp.getJobId());
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return ClientWrapper.execute(new Callback(), ipSelector.getIp());
    }

    @Override
    public ScheduleGroupResp scheduleGroup(ScheduleGroupReq req) {
        class Callback implements ClientCallback<ScheduleGroupResp> {
            @Override
            public ScheduleGroupResp doAction(TScheduleService.Client client) throws TException {
                TGroupJobReq tReq = new TGroupJobReq();
                tReq.setParameter(req.getParameter());
                tReq.setGroupId(req.getFlowGroupId());
                tReq.setAccessToken(accessToken);
                TScheduleGroupResp tResp = client.scheduleGroup(tReq);
                ScheduleGroupResp resp = new ScheduleGroupResp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                resp.setJobGroupId(tResp.getJobGroupId());
                return resp;
            }
        }
        return ClientWrapper.execute(new Callback(), ipSelector.getIp());
    }

    @Override
    public Resp ack(AckReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TScheduleService.Client client) throws TException {
                TAckReq tReq = new TAckReq();
                tReq.setJobId(req.getJobId());
                tReq.setCode(req.getCode());
                tReq.setMsg(req.getMsg());
                TResp tResp = client.ack(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return ClientWrapper.execute(new Callback(), ipSelector.getIp());
    }

    @Override
    public Resp ackMaster(AckReq req) {
        return null;
    }

    @Override
    public Resp executorHb(ExecutorHbReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TScheduleService.Client client) throws TException {
                TExecutorHbReq tReq = new TExecutorHbReq();
                tReq.setIp(req.getIp());
                tReq.setPort(req.getPort());
                tReq.setTag(req.getTag());
                TResp tResp = client.executorHb(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return ClientWrapper.execute(new Callback(), ipSelector.getIp());
    }

    @Override
    public Resp schedulerHb(ScheduleHbReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TScheduleService.Client client) throws TException {
                TScheduleHbReq tReq = new TScheduleHbReq();
                tReq.setIp(req.getIp());
                tReq.setPort(req.getPort());
                TResp tResp = client.schedulerHb(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return ClientWrapper.execute(new Callback(), ipSelector.getIp());
    }

    @Override
    public Resp updateExecutorNode(ExecutorHbReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TScheduleService.Client client) throws TException {
                TExecutorHbReq tReq = new TExecutorHbReq();
                tReq.setIp(req.getIp());
                tReq.setPort(req.getPort());
                TResp tResp = client.updateExecutorNode(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return ClientWrapper.execute(new Callback(), ipSelector.getIp());
    }
}
