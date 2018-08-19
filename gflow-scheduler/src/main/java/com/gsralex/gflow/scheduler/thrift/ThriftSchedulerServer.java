package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import com.gsralex.gflow.scheduler.SchedulerServer;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class ThriftSchedulerServer {

    private GFlowContext context;

    public ThriftSchedulerServer(GFlowContext context) {
        this.context = context;
    }

    public void start(int port) {
        TServerTransport tServerSocket;
        try {
            tServerSocket = new TServerSocket(port);

            TMultiplexedProcessor processor = new TMultiplexedProcessor();

            TScheduleService.Processor<TScheduleServiceImpl> schedule =
                    new TScheduleService.Processor<>(new TScheduleServiceImpl(context));
            processor.registerProcessor("schedule", schedule);

            TThreadPoolServer.Args args = new TThreadPoolServer.Args(tServerSocket);
            args.processor(processor);
            args.protocolFactory(new TBinaryProtocol.Factory());
            TServer tServer = new TThreadPoolServer(args);

            new Thread(() -> {
                tServer.serve();
            }).start();

        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
