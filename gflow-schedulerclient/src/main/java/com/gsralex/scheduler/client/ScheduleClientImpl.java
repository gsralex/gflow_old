package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.model.Result;
import com.gsralex.gflow.core.thriftgen.*;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class ScheduleClientImpl implements ScheduleClient {

    private static final Logger LOGGER = Logger.getLogger(ScheduleClientImpl.class);

    private SchedulerClientContext context;

    public ScheduleClientImpl(SchedulerClientContext context) {
        this.context = context;
    }


    public Result scheduleGroup(long groupId, Parameter parameter) {
        class ClientGroupCallback implements ClientCallback {

            @Override
            public Result doSchedule(TScheduleService.Client client) throws TException {
                TGroupJobDesc tDesc = new TGroupJobDesc();
                tDesc.setParameter(parameter.toString());
                tDesc.setGroupId(groupId);
                tDesc.setAccessToken(getAccessToken());
                TResult tResult = client.scheduleGroup(tDesc);
                return new Result(tResult.getCode(), tResult.getMsg());
            }
        }
        return execute(new ClientGroupCallback());
    }

    @Override
    public Result pauseGroup(long id) {
        class PauseGroupCallback implements ClientCallback {
            @Override
            public Result doSchedule(TScheduleService.Client client) throws TException {
                TGroupJobDesc tDesc = new TGroupJobDesc();
                tDesc.setGroupId(id);
                tDesc.setAccessToken(getAccessToken());
                TResult tResult = client.pauseGroup(tDesc);
                return new Result(tResult.getCode(), tResult.getMsg());
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
                TJobDesc tDesc = new TJobDesc();
                tDesc.setParameter(parameter.toString());
                tDesc.setActionId(actionId);
                tDesc.setAccessToken(getAccessToken());
                TResult tResult = client.scheduleAction(tDesc);
                return new Result(tResult.getCode(), tResult.getMsg());
            }
        }
        return execute(new ClientActionCallback());
    }

    @Override
    public Result setSettings(String key, String value) {
        class SetSettingsCallback implements ClientCallback {
            @Override
            public Result doSchedule(TScheduleService.Client client) throws TException {
                TSettingsDesc tDesc = new TSettingsDesc();
                tDesc.setKey(key);
                tDesc.setValue(value);
                tDesc.setAccessToken(getAccessToken());
                TResult tResult = client.setSettings(tDesc);
                return new Result(tResult.getCode(), tResult.getMsg());
            }
        }
        return execute(new SetSettingsCallback());
    }

    public Result execute(ClientCallback callback) {
        IpAddress ip = getIpAddress();
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "schedule");
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
