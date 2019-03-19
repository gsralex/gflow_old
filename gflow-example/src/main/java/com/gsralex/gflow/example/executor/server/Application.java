package com.gsralex.gflow.example.executor.server;

import com.gsralex.gflow.executor.ExecutorServer;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;

/**
 * @author gsralex
 * @version 2019/3/11
 */
public class Application {

    public static void main(String[] args) throws Exception {
        ExecutorServer server = new ExecutorServer();
        server.serve();
    }
}
