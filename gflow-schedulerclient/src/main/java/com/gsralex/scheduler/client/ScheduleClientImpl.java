package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.model.Result;
import com.gsralex.gflow.core.thriftgen.TGroupJobDesc;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import com.gsralex.gflow.core.util.AccessTokenUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class ScheduleClientImpl implements ScheduleClient {

    private static final Logger logger = Logger.getLogger(ScheduleClientImpl.class);

    private GFlowContext context;

    public ScheduleClientImpl() {
        context = ScheduleClientContext.getContext().getGFlowContext();
    }


    public Result scheduleGroup(long groupId, Parameter parameter) {
        class ScheduleGroupCallback implements ScheduleCallback{

            @Override
            public Result doSchedule(TScheduleService.Client client) {
                TGroupJobDesc tDesc = new TGroupJobDesc();
                tDesc.setParameter(parameter.toString());
                tDesc.setGroupId(groupId);
                String accessKey = context.getConfig().getAccessKey();
                tDesc.setAccessToken(AccessTokenUtils.encrypt(accessKey));
                TResult tResult= null;
                try {
                    tResult = client.scheduleGroup(tDesc);
                } catch (TException e) {
                    e.printStackTrace();
                }
                return new Result(tResult.getCode(),tResult.getErrmsg());
            }
        }
        return execute(new ScheduleGroupCallback());
    }

    @Override
    public Result pauseGroup(long id) {
        return null;
    }

    @Override
    public Result stopGroup(long id) {
        return null;
    }

    public Result scheduleAction(long actionId, Parameter parameter) {
        class ScheduleActionCallback implements ScheduleCallback {
            @Override
            public Result doSchedule(TScheduleService.Client client) {
                TJobDesc tDesc = new TJobDesc();
                tDesc.setParameter(parameter.toString());
                tDesc.setActionId(actionId);
                String accessKey = context.getConfig().getAccessKey();
                tDesc.setAccessToken(AccessTokenUtils.encrypt(accessKey));
                TResult tResult= null;
                try {
                    tResult = client.schedule(tDesc);
                } catch (TException e) {
                    e.printStackTrace();
                }
                return new Result(tResult.getCode(),tResult.getErrmsg());
            }

        }
        return execute(new ScheduleActionCallback());
    }

    @Override
    public Result setGflowSettings(String key, String value) {
        return null;
    }

    public Result execute(ScheduleCallback callback){
        IpAddress ip = getIpAddress();
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "schedule");
            TScheduleService.Client client = new TScheduleService.Client(multiProtocol);
            return callback.doSchedule(client);
        } catch (TTransportException e) {
            return null;
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }

    private IpAddress getIpAddress() {
        ScheduleClientContext apiContext = ScheduleClientContext.getContext();
        return apiContext.getIps().get(0);
    }


    public static void main(String[] args) {
        ScheduleClientImpl api = new ScheduleClientImpl();
        Parameter parameter = new Parameter("bizdate=20181209");
        api.scheduleGroup(1, parameter);
    }
}
