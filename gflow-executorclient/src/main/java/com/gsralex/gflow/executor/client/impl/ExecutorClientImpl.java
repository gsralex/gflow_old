package com.gsralex.gflow.executor.client.impl;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.scheduler.TExecutorService;
import com.gsralex.gflow.core.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.executor.client.ExecutorAction;
import com.gsralex.gflow.executor.client.ExecutorClient;
import com.gsralex.gflow.executor.client.action.JobReq;
import com.gsralex.gflow.executor.client.action.Resp;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/8
 */
public class ExecutorClientImpl implements ExecutorClient {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutorClientImpl.class);

    private IpAddress ip;
    private List<IpAddress> ipList;
    private int seq;

    public ExecutorClientImpl(List<IpAddress> ipList) {
        this.ipList = ipList;
    }

    public ExecutorClientImpl(IpAddress ip) {
        this.ip = ip;
    }

    @Override
    public Resp schedule(JobReq req) throws TException {
        ExecutorAction action = new ExecutorAction() {
            Resp resp;

            @Override
            public void doAction(TExecutorService.Client client) throws TException {
                TJobReq tReq = new TJobReq();
                tReq.setId(req.getId());
                tReq.setParameter(req.getParameter());
                tReq.setClassName(req.getClassName());
                tReq.setAccessToken(req.getAccessToken());
                tReq.setActionId(req.getActionId());
                tReq.setIndex(req.getIndex());
                tReq.setJobGroupId(req.getJobGroupId());
                TResp tResp = client.schedule(tReq);
                resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
            }

            @Override
            public Resp getResult() {
                return resp;
            }
        };
        wrap(action);
        return action.getResult();
    }


    private void wrap(ExecutorAction action) throws TException {
        IpAddress ip = getIp();
        LOG.info("TRpcClient.send ip:" + ip.getIp() + ",port:" + ip.getPort());
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TExecutorService.Client client = new TExecutorService.Client(protocol);
            action.doAction(client);
        } catch (TException e) {
            LOG.error("TRpcClient.send ip:" + ip.getIp() + ",port:" + ip.getPort(), e);
            throw e;
        } finally {
            if (transport != null)
                transport.close();
        }
    }

    private IpAddress getIp() {
        if (ipList != null && ipList.size() != 0) {
            seq++;
            if (seq >= ipList.size()) {
                seq = 0;
            }
            return ipList.get(seq);
        }
        return ip;
    }
}
