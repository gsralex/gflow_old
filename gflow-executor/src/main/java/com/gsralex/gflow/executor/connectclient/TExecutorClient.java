package com.gsralex.gflow.executor.connectclient;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.TAckDesc;
import com.gsralex.gflow.core.thriftgen.TScheduleAckService;
import com.gsralex.gflow.executor.ExecutorContext;
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

    private ExecutorContext context;

    public TExecutorClient(ExecutorContext context) {
        this.context = context;
    }

    public void ack(long jobId, boolean ok) {
        TTransport transport = null;
        try {
            List<IpAddress> list = context.getScheduleIps();
            IpAddress ip = list.get(0);
            transport = new TSocket(ip.getIp(), ip.getPort());
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "scheduleAck");
            TScheduleAckService.Client client = new TScheduleAckService.Client(multiProtocol);
            TAckDesc ackDesc = new TAckDesc();
            ackDesc.setJobId(jobId);
            String accessKey = context.getGFlowContext().getConfig().getAccessKey();
            ackDesc.setAccessToken(SecurityUtils.encrypt(accessKey));
            if (ok) {
                ackDesc.setCode(ErrConstants.OK);
                ackDesc.setErrmsg(ErrConstants.MSG_OK);
            } else {
                ackDesc.setCode(ErrConstants.ERR_EXECUTEFAIL);
                ackDesc.setErrmsg(ErrConstants.MSG_ERREXECUTEFAIL);
            }
            client.ack(ackDesc);
        } catch (TException e) {
            logger.error("TExecutorClient.ack", e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}
