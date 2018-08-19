package com.gsralex.gflow.executor.thrift;

import com.gsralex.gflow.core.thriftgen.TExecutorService;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class ExecutorServiceImpl implements TExecutorService.Iface {
    @Override
    public TResult schedule(TJobDesc desc) throws TException {
        return null;
    }
}
