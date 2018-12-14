package com.gsralex.gflow.scheduler.scheduleclient;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.TExecutorService;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.scheduler.server.ScheduleTransportException;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TRpcClient {

    private static final Logger LOGGER = Logger.getLogger(TRpcClient.class);

    public TResult schedule(IpAddress ip, TJobDesc jobDesc) throws ScheduleTransportException {
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TExecutorService.Client client = new TExecutorService.Client(protocol);
            return client.schedule(jobDesc);
        } catch (TException e) {
            LOGGER.error("TRpcClient.send", e);
            throw new ScheduleTransportException(e);
        } finally {
            if (transport != null)
                transport.close();
        }
    }
}
