package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.thriftgen.TScheduleAckService;
import com.gsralex.gflow.core.thriftgen.TScheduleService;
import com.gsralex.gflow.scheduler.SchedulerContext;
import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(ThriftSchedulerServer.class);


    private TScheduleAckServiceImpl scheduleAckService;
    private TScheduleServiceImpl scheduleService;
    private SchedulerContext context;

    public ThriftSchedulerServer(SchedulerContext context) {
        scheduleAckService = new TScheduleAckServiceImpl(context);
        scheduleService = new TScheduleServiceImpl(context);
        this.context=context;
    }

    public void start() throws ScheduleTransportException {
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
            LOGGER.error("ThriftSchedulerServer.start", e);
            throw new ScheduleTransportException(e);
        }
    }
}
