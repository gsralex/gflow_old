package com.gsralex.gflow.executor.thrift;

import com.gsralex.gflow.core.thrift.gen.TExecutorService;
import com.gsralex.gflow.core.thrift.gen.TJobDesc;
import com.gsralex.gflow.core.thrift.gen.TResult;
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
