package com.gsralex.scheduler.api;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.TGroupJobDesc;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
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
            TResult result = client.scheduleGroup(groupJobDesc);
            return result.isOk();
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
            TResult result = client.schedule(jobDesc);
            return result.isOk();
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
        Parameter parameter = new Parameter();
        api.scheduleGroup(1, parameter);
    }
}
