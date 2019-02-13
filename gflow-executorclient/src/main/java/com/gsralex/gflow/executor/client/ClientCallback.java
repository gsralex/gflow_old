package com.gsralex.gflow.executor.client;

import com.gsralex.gflow.core.thriftgen.scheduler.TExecutorService;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2019/2/8
 */
public interface ClientCallback<T> {

    T doAction(TExecutorService.Client client) throws TException;
}
