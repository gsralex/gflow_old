package com.gsralex.gflow.executor.thrift;

import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.TExecutorService;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.executor.AckExecuteProcess;
import com.gsralex.gflow.executor.ExecuteProcess;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.executor.ExecutorThread;
import com.gsralex.gflow.executor.demo.DemoProcess1;
import org.apache.thrift.TException;
import org.springframework.beans.BeansException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorServiceImpl implements TExecutorService.Iface {

    public static void main(String[] args) {
        System.out.println(ExecuteProcess.class.isAssignableFrom(DemoProcess1.class));
        System.out.println(AckExecuteProcess.class.isAssignableFrom(DemoProcess1.class));
    }

    private ExecutorService executorService;

    public TExecutorServiceImpl() {
        int threads = ExecutorContext.getContext().getGFlowContext().getConfig().getExecutorThreads();
        executorService = Executors.newFixedThreadPool(threads);
    }

    @Override
    public TResult schedule(TJobDesc desc) {
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
        }
        TResult tResult = new TResult();
        tResult.setCode(code);
        tResult.setErrmsg(errMsg);
        return tResult;
    }

    private Object getInstance(Class type) throws IllegalAccessException, InstantiationException {
        Object instance;
        try {
            instance = ExecutorContext.getContext().getSpringBean(type);
        } catch (BeansException e) {
            instance = type.newInstance();
        }
        return instance;
    }

    @Override
    public TResult heartbeat() throws TException {
        return new TResult(ErrConstants.OK, ErrConstants.MSG_OK);
    }
}
