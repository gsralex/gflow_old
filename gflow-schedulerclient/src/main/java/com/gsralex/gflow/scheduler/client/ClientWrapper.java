package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.thriftgen.scheduler.TScheduleService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2018/12/27
 */
public class ClientWrapper {

    private static Logger LOG = LoggerFactory.getLogger(ClientWrapper.class);

    public static <T> T execute(ClientCallback<T> callback, IpAddr ip) {
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "scheduler");
            TScheduleService.Client client = new TScheduleService.Client(multiProtocol);
            return callback.doAction(client);
        } catch (Exception e) {
            LOG.error("SchedulerClient.execute", e);
            throw new ClientTransportException(e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}
