package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.scheduler.domain.job.JobResult;
import com.gsralex.gflow.scheduler.rpc.RpcClient;
import com.gsralex.gflow.scheduler.rpc.JobDesc;
import com.gsralex.gflow.scheduler.thrift.gen.TJobDesc;
import com.gsralex.gflow.scheduler.thrift.gen.TScheduleService;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TRpcClient {

    private static final Logger logger = Logger.getLogger(TRpcClient.class);


    public JobResult schedule(JobDesc desc) {
        TTransport transport = new TSocket(desc.getIp(), desc.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TScheduleService.Client client = new TScheduleService.Client(protocol);
            TJobDesc jobDesc = TModelConverter.convertTJobDesc(desc);
            client.schedule(jobDesc);
        } catch (Throwable e) {
            logger.error("TRpcClient.send", e);
//            return new JobResult(false, e.getMessage());
        } finally {
            if (transport != null)
                transport.close();
        }
        return null;
    }
}
