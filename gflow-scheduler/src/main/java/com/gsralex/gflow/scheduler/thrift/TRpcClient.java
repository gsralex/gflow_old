package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.TExecutorService;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.scheduler.schedule.ScheduleIpSelector;
import com.gsralex.gflow.scheduler.SchedulerContext;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/3/18
 */
@Service
public class TRpcClient {

    private static final Logger logger = Logger.getLogger(TRpcClient.class);


    public static void main(String[] args) throws TException {
        TRpcClient client = new TRpcClient();
        client.schedule(new IpAddress("127.0.0.1", 10092), new TJobDesc());
    }

    public TResult schedule(IpAddress ip, TJobDesc jobDesc) throws TException {
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TExecutorService.Client client = new TExecutorService.Client(protocol);
            return client.schedule(jobDesc);
        } catch (Throwable e) {
            logger.error("TRpcClient.send", e);
            throw e;
        } finally {
            if (transport != null)
                transport.close();
        }
    }
}
