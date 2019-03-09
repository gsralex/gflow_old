package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.pub.thriftgen.scheduler.TScheduleService;
import com.gsralex.gflow.pub.thriftgen.timer.TTimerService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gsralex
 * @version 2018/3/18
 */
@Component
public class TSchedulerServer {

    private static final Logger LOG = LoggerFactory.getLogger(TSchedulerServer.class);

    @Autowired
    private TScheduleServiceImpl scheduleService;
    @Autowired
    private TTimerServiceImpl timerService;

    public void start(int port) throws ScheduleTransportException {
        TServerTransport tServerSocket;
        try {
            tServerSocket = new TServerSocket(port);
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            TScheduleService.Processor<TScheduleServiceImpl> schedule =
                    new TScheduleService.Processor<>(scheduleService);
            processor.registerProcessor("scheduler", schedule);
            TTimerService.Processor<TTimerServiceImpl> timer = new TTimerService.Processor<>(timerService);
            processor.registerProcessor("timer", timer);
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(tServerSocket);
            args.processor(processor);
            args.protocolFactory(new TBinaryProtocol.Factory());
            TServer tServer = new TThreadPoolServer(args);
            new Thread(() -> {
                tServer.serve();
            }).start();

        } catch (TTransportException e) {
            LOG.error("TSchedulerServer.start", e);
            throw new ScheduleTransportException(e);
        }
    }
}
