package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.scheduler.TScheduleService;
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

    private static Logger LOGGER = LoggerFactory.getLogger(ClientWrapper.class);

    private ClientContext context;

    public ClientWrapper(ClientContext context) {
        this.context = context;
    }

    private IpAddress getIpAddress() {
        return context.getIps().get(0);
    }


    public <T> T execute(ClientCallback callback) {
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
}
