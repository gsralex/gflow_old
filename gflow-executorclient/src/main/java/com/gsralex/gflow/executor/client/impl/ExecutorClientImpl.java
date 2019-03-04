package com.gsralex.gflow.executor.client.impl;

import com.gsralex.gflow.executor.client.ClientCallback;
import com.gsralex.gflow.executor.client.ExecutorClient;
import com.gsralex.gflow.executor.client.action.JobReq;
import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.scheduler.TExecutorService;
import com.gsralex.gflow.pub.thriftgen.scheduler.TJobReq;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2019/2/8
 */
public class ExecutorClientImpl implements ExecutorClient {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutorClientImpl.class);

    private IpAddr ip;
    private String accessToken;

    public ExecutorClientImpl(IpAddr ip, String accessToken) {
        this.ip = ip;
        this.accessToken = accessToken;
    }

    @Override
    public Resp schedule(JobReq req) throws TException {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TExecutorService.Client client) throws TException {
                TJobReq tReq = new TJobReq();
                tReq.setId(req.getId());
                tReq.setParameter(req.getParameter());
                tReq.setClassName(req.getClassName());
                tReq.setAccessToken(req.getAccessToken());
                tReq.setActionId(req.getActionId());
                tReq.setIndex(req.getIndex());
                tReq.setJobGroupId(req.getJobGroupId());
                tReq.setAccessToken(accessToken);
                TResp tResp = client.schedule(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }

        return execute(new Callback(), ip);
    }

    public <T> T execute(ClientCallback<T> callback, IpAddr ip) throws TException {
        LOG.debug("TRpcClient.send ip:" + ip.getIp() + ",port:" + ip.getPort());
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TExecutorService.Client client = new TExecutorService.Client(protocol);
            return callback.doAction(client);
        } catch (TException e) {
            LOG.error("ExecutorClientImpl.execute ip:" + ip.getIp() + ",port:" + ip.getPort(), e);
            throw e;
        } finally {
            if (transport != null)
                transport.close();
        }
    }
}
