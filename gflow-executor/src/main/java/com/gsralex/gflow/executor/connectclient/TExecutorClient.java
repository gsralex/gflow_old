package com.gsralex.gflow.executor.connectclient;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.scheduler.TAckReq;
import com.gsralex.gflow.core.thriftgen.scheduler.TScheduleService;
import com.gsralex.gflow.executor.ExecutorContext;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorClient {

    private static final Logger logger = LoggerFactory.getLogger(TExecutorClient.class);

    private ExecutorContext context;

    public TExecutorClient(ExecutorContext context) {
        this.context = context;
    }

    public static void main(String[] args) throws IOException {
        ExecutorContext context = ExecutorContext.getInstance();
        context.init();
        TExecutorClient client = new TExecutorClient(context);
        client.ack(1, true);
    }

    public void ack(long jobId, boolean ok) {
        TTransport transport = null;
        try {
            List<IpAddress> list = context.getScheduleIps();
            IpAddress ip = list.get(0);
            transport = new TSocket(ip.getIp(), ip.getPort());
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "scheduler");
            TScheduleService.Client client = new TScheduleService.Client(multiProtocol);
            TAckReq req = new TAckReq();
            req.setJobId(jobId);
            String accessKey = context.getConfig().getAccessKey();
            req.setAccessToken(SecurityUtils.encrypt(accessKey));
            if (ok) {
                req.setCode(ErrConstants.OK);
                req.setErrmsg(ErrConstants.MSG_OK);
            } else {
                req.setCode(ErrConstants.ERR_EXECUTEFAIL);
                req.setErrmsg(ErrConstants.MSG_ERREXECUTEFAIL);
            }
            client.ack(req);
        } catch (TException e) {
            logger.error("TExecutorClient.ack", e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}
