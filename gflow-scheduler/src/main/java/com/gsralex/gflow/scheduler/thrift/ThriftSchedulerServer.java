package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thriftgen.TScheduleAckService;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import com.gsralex.gflow.scheduler.SchedulerServer;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gsralex
 * @version 2018/3/18
 */
@Component
public class ThriftSchedulerServer {

    private GFlowContext context;
    @Autowired
    private TScheduleAckServiceImpl scheduleAckService;
    @Autowired
    private TScheduleServiceImpl scheduleService;

    public ThriftSchedulerServer(GFlowContext context) {
        this.context = context;
    }

    public void start() {
        TServerTransport tServerSocket;
        try {
            tServerSocket = new TServerSocket(context.getConfig().getPort());
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            TScheduleService.Processor<TScheduleServiceImpl> schedule =
                    new TScheduleService.Processor<>(scheduleService);
            processor.registerProcessor("schedule", schedule);

            TScheduleAckService.Processor<TScheduleAckServiceImpl> scheduleAck =
                    new TScheduleAckService.Processor<>(scheduleAckService);
            processor.registerProcessor("scheduleAck", scheduleAck);

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
