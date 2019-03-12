package com.gsralex.gflow.scheduler.client.impl;

import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.scheduler.*;
import com.gsralex.gflow.scheduler.client.ClientTransportException;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class SchedulerClientImpl implements SchedulerClient {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerClientImpl.class);

    private String accessToken;
    private IpAddr ip;

    public SchedulerClientImpl(IpAddr ip, String accessToken) {
        this.ip = ip;
        this.accessToken = accessToken;
    }

    public static <T> T execute(ClientCallback<T> callback, IpAddr ip) {
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "scheduler");
            TScheduleService.Client client = new TScheduleService.Client(multiProtocol);
            return callback.doAction(client);
        } catch (Exception e) {
            LOG.error("SchedulerClient.execute", e);
            throw new ClientTransportException(e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
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
        return execute(new Callback(), this.ip);
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
        return execute(new Callback(), this.ip);
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
        return execute(new Callback(), this.ip);
    }

    @Override
    public Resp ackMaster(AckReq req) {
        return null;
    }

    @Override
    public SchedulerNodeResp executorHb(ExecutorHbReq req) {
        class Callback implements ClientCallback<SchedulerNodeResp> {
            @Override
            public SchedulerNodeResp doAction(TScheduleService.Client client) throws TException {
                TExecutorHbReq tReq = new TExecutorHbReq();
                tReq.setIp(req.getIp());
                tReq.setPort(req.getPort());
                tReq.setTag(req.getTag());
                TNodeResp tResp = client.executorHb(tReq);
                SchedulerNodeResp resp = new SchedulerNodeResp();
                List<IpAddr> nodeList = new ArrayList<>();
                for (TNode tNode : tResp.getNodeList()) {
                    nodeList.add(new IpAddr(tNode.getIp(), tNode.getPort()));
                }
                resp.setNodeList(nodeList);
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return execute(new Callback(), this.ip);
    }

    @Override
    public ExecutorNodeResp schedulerHb(ScheduleHbReq req) {
        class Callback implements ClientCallback<ExecutorNodeResp> {
            @Override
            public ExecutorNodeResp doAction(TScheduleService.Client client) throws TException {
                TScheduleHbReq tReq = new TScheduleHbReq();
                tReq.setIp(req.getIp());
                tReq.setPort(req.getPort());
                TNodeResp tResp = client.schedulerHb(tReq);
                ExecutorNodeResp resp=new ExecutorNodeResp();
                List<Node> nodeList = new ArrayList<>();
                for (TNode tNode : tResp.getNodeList()) {
                    IpAddr ip=new IpAddr(tNode.getIp(), tNode.getPort());
                    Node node=new Node();
                    node.setIp(ip);
                    node.setTag(tNode.getTag());
                    nodeList.add(node);
                }
                resp.setNodeList(nodeList);
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return execute(new Callback(), this.ip);
    }


    public interface ClientCallback<T> {
        T doAction(TScheduleService.Client client) throws TException;
    }


    public static void main(String[] args) {
        SchedulerClient client = SchedulerClientFactory.createScheduler(new IpAddr("127.0.0.1:20091"), "");
        ScheduleGroupReq req = new ScheduleGroupReq();
        req.setFlowGroupId(1);
        req.setParameter("bizdate=20190310");
        client.scheduleGroup(req);
    }

}
