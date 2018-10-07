package com.gsralex.gflow.executor.thrift;

import com.gsralex.gflow.core.thriftgen.TExecutorService;
import com.gsralex.gflow.executor.ExecutorContext;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorServer {

    private ExecutorContext context;

    public TExecutorServer(ExecutorContext context) {
        this.context = context;
    }

    public void start(int port) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                TServerSocket serverSocket;
                try {
                    serverSocket = new TServerSocket(port);
                    TExecutorService.Processor<TExecutorServiceImpl> schedule =
                            new TExecutorService.Processor<>(new TExecutorServiceImpl(context));

                    TProcessor processor = new TExecutorService.Processor(new TExecutorServiceImpl(context));
                    TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverSocket);
                    args.processor(processor);
                    args.protocolFactory(new TBinaryProtocol.Factory());
                    TServer tServer = new TThreadPoolServer(args);

                    tServer.serve();

                } catch (TTransportException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }


}
