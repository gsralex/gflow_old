package com.gsralex.gflow.executor.thrift;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorClient {

    private static final Logger logger = Logger.getLogger(TExecutorClient.class);

    private GFlowContext context;

    public TExecutorClient(GFlowContext context) {
        this.context = context;
    }

    public void ack(long jobId, boolean ok) {
        TTransport transport = null;
        try {
            List<IpAddress> list = context.getZkContext().getScheduleIps();
            IpAddress ip = list.get(0);
            transport = new TSocket(ip.getIp(), ip.getPort());
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "schedule");
            TScheduleService.Client client = new TScheduleService.Client(multiProtocol);
            client.ack(jobId, ok);
        } catch (TException e) {
            logger.error("TExecutorClient.ack", e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}
