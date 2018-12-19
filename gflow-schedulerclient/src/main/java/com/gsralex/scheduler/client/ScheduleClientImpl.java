package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.model.Result;
import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.scheduler.TGroupJobReq;
import com.gsralex.gflow.core.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.core.thriftgen.scheduler.TScheduleService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class ScheduleClientImpl implements ScheduleClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleClientImpl.class);

    private SchedulerClientContext context;

    public ScheduleClientImpl(SchedulerClientContext context) {
        this.context = context;
    }


    public Result scheduleGroup(long groupId, Parameter parameter) {
        class ClientGroupCallback implements ClientCallback {

            @Override
            public Result doSchedule(TScheduleService.Client client) throws TException {
                TGroupJobReq req = new TGroupJobReq();
                req.setParameter(parameter.toString());
                req.setGroupId(groupId);
                req.setAccessToken(getAccessToken());
                TResp resp = client.scheduleGroup(req);
                return new Result(resp.getCode(), resp.getMsg());
            }
        }
        return execute(new ClientGroupCallback());
    }

    @Override
    public Result pauseGroup(long id) {
        class PauseGroupCallback implements ClientCallback {
            @Override
            public Result doSchedule(TScheduleService.Client client) throws TException {
                TGroupJobReq req = new TGroupJobReq();
                req.setGroupId(id);
                req.setAccessToken(getAccessToken());
                TResp resp = client.pauseGroup(req);
                return new Result(resp.getCode(), resp.getMsg());
            }
        }
        return execute(new PauseGroupCallback());
    }

    @Override
    public Result stopGroup(long id) {
        return null;
    }

    public Result scheduleAction(long actionId, Parameter parameter) {
        class ClientActionCallback implements ClientCallback {
            @Override
            public Result doSchedule(TScheduleService.Client client) throws TException {
                TJobReq req = new TJobReq();
                req.setParameter(parameter.toString());
                req.setActionId(actionId);
                req.setAccessToken(getAccessToken());
                TResp resp = client.scheduleAction(req);
                return new Result(resp.getCode(), resp.getMsg());
            }
        }
        return execute(new ClientActionCallback());
    }


    public Result execute(ClientCallback callback) {
        IpAddress ip = getIpAddress();
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "scheduler");
            TScheduleService.Client client = new TScheduleService.Client(multiProtocol);
            return callback.doSchedule(client);
        } catch (Exception e) {
            LOGGER.error("ScheduleClient.execute", e);
            throw new ClientTransportException(e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    private IpAddress getIpAddress() {
        return context.getIps().get(0);
    }

    private String getAccessToken() {
        String accessKey = context.getConfig().getAccessKey();
        return SecurityUtils.encrypt(accessKey);
    }
}
