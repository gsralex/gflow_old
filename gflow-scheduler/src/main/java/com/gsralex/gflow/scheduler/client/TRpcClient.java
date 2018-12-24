package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.TResp;
import com.gsralex.gflow.core.thriftgen.scheduler.TExecutorService;
import com.gsralex.gflow.core.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.scheduler.server.ScheduleTransportException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TRpcClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TRpcClient.class);

    public TResp schedule(IpAddress ip, TJobReq jobDesc) throws ScheduleTransportException {
        LOGGER.info("TRpcClient.send ip:" + ip.getIp() + ",port:" + ip.getPort());
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TExecutorService.Client client = new TExecutorService.Client(protocol);
            return client.schedule(jobDesc);
        } catch (TException e) {
            LOGGER.error("TRpcClient.send ip:" + ip.getIp() + ",port:" + ip.getPort(), e);
            throw new ScheduleTransportException(e);
        } finally {
            if (transport != null)
                transport.close();
        }
    }
}
