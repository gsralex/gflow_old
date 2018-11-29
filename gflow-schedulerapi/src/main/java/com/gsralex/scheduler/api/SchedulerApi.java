package com.gsralex.scheduler.api;

import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.TGroupJobDesc;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import com.gsralex.gflow.core.util.AccessTokenUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class SchedulerApi {

    private static final Logger logger = Logger.getLogger(SchedulerApi.class);

    private GFlowContext context;

    public SchedulerApi() {
        context = SchedulerApiContext.getContext().getGFlowContext();
    }


    public boolean scheduleGroup(long groupId, Parameter parameter) {
        IpAddress ip = getIpAddress();
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "schedule");
            TScheduleService.Client client = new TScheduleService.Client(multiProtocol);
            TGroupJobDesc groupJobDesc = new TGroupJobDesc();
            groupJobDesc.setParameter(parameter.toString());
            groupJobDesc.setGroupId(groupId);
            String accessKey = context.getConfig().getAccessKey();
            groupJobDesc.setAccessToken(AccessTokenUtils.encrypt(accessKey));
            TResult result = client.scheduleGroup(groupJobDesc);
            return result.getCode() == ErrConstants.OK;
        } catch (Throwable e) {
            logger.error("SchedulerApi.scheduleGroup", e);
        } finally {
            if (transport != null)
                transport.close();
        }
        return false;

    }

    public boolean scheduleAction(long actionId, Parameter parameter) {
        IpAddress ip = getIpAddress();
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "schedule");
            TScheduleService.Client client = new TScheduleService.Client(multiProtocol);
            TJobDesc jobDesc = new TJobDesc();
            jobDesc.setParameter(parameter.toString());
            jobDesc.setActionId(actionId);
            String accessKey = context.getConfig().getAccessKey();
            jobDesc.setAccessToken(AccessTokenUtils.encrypt(accessKey));
            TResult result = client.schedule(jobDesc);
            return result.getCode() == ErrConstants.OK;
        } catch (Throwable e) {

        } finally {
            if (transport != null)
                transport.close();
        }
        return false;
    }

    private IpAddress getIpAddress() {
        SchedulerApiContext apiContext = SchedulerApiContext.getContext();
        return apiContext.getIps().get(0);
    }

    public static void main(String[] args) {
        SchedulerApi api = new SchedulerApi();
        Parameter parameter = new Parameter("bizdate=20181128");
        api.scheduleGroup(1, parameter);
    }
}
