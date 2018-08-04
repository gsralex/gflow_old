package com.gsralex.gflow.executor.thrift;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.thrift.gen.TExecutorService;
import com.gsralex.gflow.core.thrift.gen.TJobDesc;
import com.gsralex.gflow.core.thrift.gen.TResult;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorServiceImpl implements TExecutorService.Iface {


    private GFlowContext context;

    public TExecutorServiceImpl(GFlowContext context) {
        this.context = context;
    }

    @Override
    public TResult schedule(TJobDesc desc) throws TException {
        return null;
    }
}
