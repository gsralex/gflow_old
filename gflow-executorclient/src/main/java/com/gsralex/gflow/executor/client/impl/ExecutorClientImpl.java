package com.gsralex.gflow.executor.client.impl;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.scheduler.TExecutorService;
import com.gsralex.gflow.core.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.core.util.IpSelector;
import com.gsralex.gflow.executor.client.ClientCallback;
import com.gsralex.gflow.executor.client.ClientWrapper;
import com.gsralex.gflow.executor.client.ExecutorClient;
import com.gsralex.gflow.executor.client.action.JobReq;
import com.gsralex.gflow.executor.client.action.Resp;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/8
 */
public class ExecutorClientImpl implements ExecutorClient {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutorClientImpl.class);

    private IpSelector ipSelector;
    private String accessToken;


    public ExecutorClientImpl(List<IpAddr> ipList, String accessToken) {
        this.ipSelector = new IpSelector(ipList);
        this.accessToken = accessToken;
    }

    public ExecutorClientImpl(IpAddr ip, String accessToken) {
        this.ipSelector = new IpSelector(ip);
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

        return ClientWrapper.execute(new Callback(), ipSelector.getIp());
    }
}
