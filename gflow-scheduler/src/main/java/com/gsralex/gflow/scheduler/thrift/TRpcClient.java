package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.thrift.TJobDesc;
import com.gsralex.gflow.core.thrift.TJobResult;
import com.gsralex.gflow.core.thrift.TScheduleService;
import com.gsralex.gflow.scheduler.rpc.RpcResult;
import com.gsralex.gflow.scheduler.rpc.RpcClient;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * com.gsralex.gflow.scheduler.thrift com.gsralex.gflow.scheduler.rpc client
 *
 * @author gsralex
 * @date 2018/2/18
 */
public class TRpcClient implements RpcClient {

    private static Logger LOGGER = Logger.getLogger(TRpcClient.class);

    @Override
    public RpcResult send(String ip, int port, long actionId, String parameter) {
        RpcResult r = new RpcResult();
        TTransport transport = new TSocket(ip, port);
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TScheduleService.Client client = new TScheduleService.Client(protocol);
            TJobDesc desc = new TJobDesc();
            desc.setId(actionId);
            desc.setParameter(parameter);
            TJobResult tJobResult = client.schedule(desc);

        } catch (Throwable e) {
            LOGGER.error("TRpcClient.send", e);
        } finally {
            if (transport != null)
                transport.close();
        }
        return r;
    }
}
