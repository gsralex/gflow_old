package com.gsralex.gflow.executor.server;

import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.TExecutorService;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.executor.AckExecuteProcess;
import com.gsralex.gflow.executor.ExecuteProcess;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.executor.ExecutorThread;
import com.gsralex.gflow.executor.config.ExecutorConfig;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorServiceImpl implements TExecutorService.Iface {


    private static final Logger LOGGER = Logger.getLogger(TExecutorServiceImpl.class);
    private ExecutorService executorService;

    private ExecutorContext context;
    public TExecutorServiceImpl(ExecutorContext context) {
        this.context=context;
        int threads = 10;
        executorService = Executors.newFixedThreadPool(threads);
    }

    @Override
    public TResult schedule(TJobDesc desc) throws TException {
        String errMsg = "";
        int code;
        Parameter parameter = new Parameter(desc.getParameter());
        try {
            Class type = Class.forName(desc.getClassName());
            if (ExecuteProcess.class.isAssignableFrom(type)) {
                ExecuteProcess process = (ExecuteProcess) getInstance(type);
                ExecutorThread thread = new ExecutorThread(process);
                thread.setTJobDesc(desc);
                thread.setParameter(parameter);
                executorService.execute(thread);
                code = ErrConstants.OK;
            } else if (AckExecuteProcess.class.isAssignableFrom(type)) {
                AckExecuteProcess process = (AckExecuteProcess) getInstance(type);
                ExecutorThread thread = new ExecutorThread(process);
                thread.setTJobDesc(desc);
                thread.setParameter(parameter);
                executorService.execute(thread);
                code = ErrConstants.OK;
            } else {
                //TODO:throw exception
                code = ErrConstants.ERR_NOIMPLPROCESS;
                errMsg = ErrConstants.MSG_ERRNOIMPLPROCESS;
            }
        } catch (Exception e) {
            errMsg = e.getMessage();
            code = ErrConstants.ERR_INTERNAL;
            LOGGER.error("TExecutorServiceImpl.schedule", e);
        }
        TResult tResult = new TResult();
        tResult.setCode(code);
        tResult.setMsg(errMsg);
        return tResult;
    }

    private Object getInstance(Class type) throws IllegalAccessException, InstantiationException {
        Object instance;
        if (context.isSpring()) {
            instance = context.getSpringBean(type);
        } else {
            instance = type.newInstance();
        }
        return instance;
    }

    @Override
    public TResult heartbeat() throws TException {
        return new TResult(ErrConstants.OK, ErrConstants.MSG_OK);
    }
}
