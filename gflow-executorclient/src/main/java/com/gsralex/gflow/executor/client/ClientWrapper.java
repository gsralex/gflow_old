package com.gsralex.gflow.executor.client;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.scheduler.TExecutorService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2019/2/13
 */
public class ClientWrapper {

    private static Logger LOG = LoggerFactory.getLogger(ClientWrapper.class);


    public static <T> T wrap(ClientCallback<T> callback, IpAddress ip) throws TException {
        LOG.debug("TRpcClient.send ip:" + ip.getIp() + ",port:" + ip.getPort());
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TExecutorService.Client client = new TExecutorService.Client(protocol);
            return callback.doAction(client);
        } catch (TException e) {
            LOG.error("TRpcClient.send ip:" + ip.getIp() + ",port:" + ip.getPort(), e);
            throw e;
        } finally {
            if (transport != null)
                transport.close();
        }
    }
}
