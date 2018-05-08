package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.scheduler.SchedulerServer;
import com.gsralex.gflow.scheduler.thrift.gen.TScheduleService;
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
public class ThriftSchedulerServer implements SchedulerServer {
    @Override
    public void start(int port) {
        TServerTransport tServerSocket = null;
        try {
            tServerSocket = new TServerSocket(port);

            TMultiplexedProcessor processor = new TMultiplexedProcessor();

            TScheduleService.Processor<TScheduleServiceImpl> schedule =
                    new TScheduleService.Processor<>(new TScheduleServiceImpl());
            processor.registerProcessor("schedule", schedule);

//            TScheduleService.Processor<TReplyServiceImpl> reply =
//                    new TReplyServie.Processor<>(new TReplyServiceImpl());
//            processor.registerProcessor("reply", reply);

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

    public static void main(String[] args) {
        SchedulerServer server = new ThriftSchedulerServer();
        server.start(8010);
    }
}
