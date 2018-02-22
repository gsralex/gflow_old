package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.constant.MessageConstants;
import com.gsralex.gflow.core.thrift.TReplyServie;
import com.gsralex.gflow.core.thrift.TScheduleService;
import com.gsralex.gflow.scheduler.SchedulerServer;
import org.apache.log4j.Logger;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public class TScheduleServer implements SchedulerServer {

    private static final String SERVER_NAME = "Thrift";
    private static final Logger LOGGER = Logger.getLogger(TScheduleServer.class);


    public void start(int port, boolean autoTask) {
        try {
            TServerTransport tServerSocket = new TServerSocket(port);
            TMultiplexedProcessor processor = new TMultiplexedProcessor();

            TScheduleService.Processor<TScheduleServiceImpl> schedule=
                    new TScheduleService.Processor<>(new TScheduleServiceImpl());
            processor.registerProcessor("schedule",schedule);

            TReplyServie.Processor<TReplyServiceImpl> reply=
                    new TReplyServie.Processor<>(new TReplyServiceImpl());
            processor.registerProcessor("reply",reply);

            TThreadPoolServer.Args args = new TThreadPoolServer.Args(tServerSocket);
            args.processor(processor);
            args.protocolFactory(new TBinaryProtocol.Factory());
            TServer tServer = new TThreadPoolServer(args);
            start(tServer);
        } catch (Throwable t) {

        }
    }

    public void start(TServer tServer) {
        new Thread(() -> {
            try {
                tServer.serve();
                LOGGER.info(String.format(MessageConstants.SCHEDULE_START, SERVER_NAME));
            } catch (Throwable t) {
                LOGGER.error("TScheduleServer.start", t);
            }
        }).start();
    }

    public static void main(String[] args) {
        SchedulerServer schedulerServer = new TScheduleServer();
        schedulerServer.start(8090, true);
    }
}
