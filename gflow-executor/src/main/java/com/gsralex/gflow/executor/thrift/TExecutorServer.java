package com.gsralex.gflow.executor.thrift;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thrift.gen.TExecutorService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorServer implements ExecutorServer {

    private GFlowContext context;

    public TExecutorServer(GFlowContext context) {
        this.context = context;
    }

    public void start(int port) {
        TServerTransport serverTransport;
        try {
            serverTransport = new TServerSocket(port);
            TMultiplexedProcessor processor = new TMultiplexedProcessor();

            TExecutorService.Processor<TExecutorServiceImpl> schedule =
                    new TExecutorService.Processor<>(new TExecutorServiceImpl(this.context));
            processor.registerProcessor("schedule", schedule);

            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
            args.processor(processor);
            args.protocolFactory(new TBinaryProtocol.Factory());
            TServer tServer = new TThreadPoolServer(args);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    tServer.serve();
                }
            });
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }


}
