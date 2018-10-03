package com.gsralex.scheduler.api;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.TGroupJobDesc;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import com.gsralex.gflow.core.zk.SchedulerIpData;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class SchedulerApi {


    public SchedulerApi() {
        GFlowContext context = GFlowContext.getContext();
        if (context.getConfig() == null) {
            context.initConfig();
            if(context.getConfig().getZkActive()!=null && context.getConfig().getZkActive()){
                context.initZk();
            }

            SchedulerIpData ipData=new SchedulerIpData(context);
        }
    }

    public boolean scheduleGroup(long groupId, Parameter parameter) {
        String ip = "";
        int port = 0;
        TTransport transport = new TSocket(ip, port);
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TScheduleService.Client client = new TScheduleService.Client(protocol);
            TGroupJobDesc groupJobDesc = new TGroupJobDesc();
            groupJobDesc.setParameter(parameter.toString());
            groupJobDesc.setGroupId(groupId);
            TResult result = client.scheduleGroup(groupJobDesc);
            return result.isOk();
        } catch (Throwable e) {

        } finally {
            if (transport != null)
                transport.close();
        }
        return false;

    }

    public boolean scheduleAction(long actionId, Parameter parameter) {
        String ip = "";
        int port = 0;
        TTransport transport = new TSocket(ip, port);
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TScheduleService.Client client = new TScheduleService.Client(protocol);
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

    public static void main(String[] args) {
        SchedulerApi api = new SchedulerApi();
        Parameter parameter = new Parameter();
        api.scheduleGroup(1, parameter);
    }
}
