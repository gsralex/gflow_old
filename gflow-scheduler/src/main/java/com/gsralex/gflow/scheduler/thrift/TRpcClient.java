package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.TExecutorService;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TRpcClient {

    private static final Logger logger = Logger.getLogger(TRpcClient.class);

    private GFlowContext context;

    public TRpcClient(GFlowContext context) {
        this.context = context;
    }


    public TResult schedule(TJobDesc jobDesc) {
        List<IpAddress> ipList = this.context.getZkContext().getExecutorIps();
        IpAddress ip = ipList.get(0);
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TExecutorService.Client client = new TExecutorService.Client(protocol);
            return client.schedule(jobDesc);
        } catch (Throwable e) {
            logger.error("TRpcClient.send", e);
        } finally {
            if (transport != null)
                transport.close();
        }
        return null;
    }
}
