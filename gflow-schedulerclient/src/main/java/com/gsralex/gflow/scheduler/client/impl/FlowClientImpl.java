package com.gsralex.gflow.scheduler.client.impl;

import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.flow.TFlowDirect;
import com.gsralex.gflow.pub.thriftgen.flow.TFlowGroup;
import com.gsralex.gflow.pub.thriftgen.flow.TFlowItem;
import com.gsralex.gflow.pub.thriftgen.flow.TFlowService;
import com.gsralex.gflow.scheduler.client.ClientTransportException;
import com.gsralex.gflow.scheduler.client.FlowClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.flow.FlowDirect;
import com.gsralex.gflow.scheduler.client.action.flow.FlowGroup;
import com.gsralex.gflow.scheduler.client.action.flow.FlowItem;
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
 * @version 2019/3/10
 */
public class FlowClientImpl implements FlowClient {

    private static final Logger LOG = LoggerFactory.getLogger(FlowClientImpl.class);
    private IpAddr ip;

    public FlowClientImpl(IpAddr ip) {
        this.ip = ip;
    }

    @Override
    public Resp setFlowGroup(FlowGroup group) {
        TFlowGroup tGroup = new TFlowGroup();
        tGroup.setId(group.getId());
        tGroup.setName(group.getName());
        tGroup.setDescription(group.getDescription());
        List<TFlowItem> tItemList = new ArrayList<>();
        for (FlowItem item : group.getItemList()) {
            TFlowItem tItem = new TFlowItem();
            tItem.setParameter(item.getParameter());
            tItem.setActionId(item.getActionId());
            tItem.setIndex(item.getIndex());
            tItem.setLabel(item.getLabel());
            tItemList.add(tItem);
        }
        tGroup.setItemList(tItemList);

        List<TFlowDirect> tDirectList = new ArrayList<>();
        for (FlowDirect direct : group.getDirectList()) {
            TFlowDirect tDirect = new TFlowDirect();
            tDirect.setIndex(direct.getIndex());
            tDirect.setNextIndex(direct.getNextIndex());
            tDirectList.add(tDirect);
        }
        tGroup.setDirectList(tDirectList);
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TFlowService.Client client) throws TException {
                TResp tResp = client.setFlowGroup(tGroup);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return execute(new Callback(), ip);
    }

    public interface ClientCallback<T> {
        T doAction(TFlowService.Client client) throws TException;
    }

    public static <T> T execute(ClientCallback<T> callback, IpAddr ip) {
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "flow");
            TFlowService.Client client = new TFlowService.Client(multiProtocol);
            return callback.doAction(client);
        } catch (Exception e) {
            LOG.error("FlowClientImpl.execute", e);
            throw new ClientTransportException(e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    public static void main(String[] args) {
        FlowClient flowClient = SchedulerClientFactory.createFlow(new IpAddr("localhost:20091"), "");
        FlowGroup flowGroup = new FlowGroup();
        flowGroup.setId(1);
        flowGroup.setName("test flow");
        flowGroup.setDescription("test flow");
        flowGroup.action(1).parameter("bizdate=${bizdate}").index(1).label("example1");
        flowGroup.action(2).parameter("bizdate=${bizdate}").index(2).label("example2");
        flowGroup.action(3).parameter("bizdate=${bizdate}").index(3).label("ackexample");
        flowGroup.action(4).parameter("bizdate=${bizdate}").index(4).label("error");

        flowGroup.direct(0).next(1);
        flowGroup.direct(1).next(2, 3);
        flowGroup.direct(2).next(4);
        flowGroup.direct(3).next(4);
        flowClient.setFlowGroup(flowGroup);
    }
}
