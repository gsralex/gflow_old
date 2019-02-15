package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.pub.thriftgen.scheduler.TScheduleService;
import com.gsralex.gflow.scheduler.SchedulerContext;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TSchedulerServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TSchedulerServer.class);

    private TScheduleServiceImpl scheduleService;
    private SchedulerContext context;

    public TSchedulerServer(SchedulerContext context) {
        scheduleService = new TScheduleServiceImpl(context);
        this.context = context;
    }

    public void start() throws ScheduleTransportException {
        TServerTransport tServerSocket;
        try {
            tServerSocket = new TServerSocket(context.getConfig().getPort());
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            TScheduleService.Processor<TScheduleServiceImpl> schedule =
                    new TScheduleService.Processor<>(scheduleService);
            processor.registerProcessor("scheduler", schedule);


            TThreadPoolServer.Args args = new TThreadPoolServer.Args(tServerSocket);
            args.processor(processor);
            args.protocolFactory(new TBinaryProtocol.Factory());
            TServer tServer = new TThreadPoolServer(args);
            new Thread(() -> {
                tServer.serve();
            }).start();

        } catch (TTransportException e) {
            LOGGER.error("TSchedulerServer.start", e);
            throw new ScheduleTransportException(e);
        }
    }
}
