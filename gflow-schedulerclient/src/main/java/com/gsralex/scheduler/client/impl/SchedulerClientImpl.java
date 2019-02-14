package com.gsralex.scheduler.client.impl;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.scheduler.*;
import com.gsralex.gflow.core.util.IpSelector;
import com.gsralex.gflow.executor.client.action.Resp;
import com.gsralex.scheduler.client.ClientCallback;
import com.gsralex.scheduler.client.ClientWrapper;
import com.gsralex.scheduler.client.SchedulerClient;
import com.gsralex.scheduler.client.action.scheduler.*;
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
    public GetJobGroupResp getJobGroup(long id) {
        return null;
    }

//    @Override
//    public GetJobGroupResp getJobGroup(long id) {
//        class Callback implements ClientCallback<GetJobGroupResp> {
//            @Override
//            public GetJobGroupResp doAction(TScheduleService.Client client) throws TException {
//                TGetJobGroupReq req = new TGetJobGroupReq();
//                req.setAccessToken(accessToken);
//                req.setId(id);
//                TGetJobGroupResp tResp = client.getGroup(req);
//                GetJobGroupResp resp = new GetJobGroupResp();
//                if (tResp.getJobGroup() != null) {
//                    resp.setJobGroupId(tResp.getJobGroup().getId());
//                    resp.setStatus(JobGroupStatusEnum.
//                            valueOf(tResp.getJobGroup().getStatus().getValue()));
//                }
//                return resp;
//            }
//        }
//        return ClientWrapper.execute(new Callback(), ipSelector.getIp());
//    }

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
                tReq.setOnline(req.isOnline());
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
