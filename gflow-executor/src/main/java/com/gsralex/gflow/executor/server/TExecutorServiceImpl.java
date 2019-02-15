package com.gsralex.gflow.executor.server;

import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.Parameter;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.scheduler.TExecutorService;
import com.gsralex.gflow.pub.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.executor.AckExecuteProcess;
import com.gsralex.gflow.executor.ExecuteProcess;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.executor.ExecutorThread;
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
        this.context = context;
        int threads = 10;
        executorService = Executors.newFixedThreadPool(threads);
    }

    @Override
    public TResp schedule(TJobReq req) throws TException {
        String errMsg = "";
        int code;
        Parameter parameter = new Parameter(req.getParameter());
        try {
            Class type = Class.forName(req.getClassName());
            if (ExecuteProcess.class.isAssignableFrom(type)) {
                ExecuteProcess process = (ExecuteProcess) getInstance(type);
                ExecutorThread thread = new ExecutorThread(process, this.context);
                thread.setReq(req);
                thread.setParameter(parameter);
                executorService.execute(thread);
                code = ErrConstants.OK;
            } else if (AckExecuteProcess.class.isAssignableFrom(type)) {
                AckExecuteProcess process = (AckExecuteProcess) getInstance(type);
                ExecutorThread thread = new ExecutorThread(process);
                thread.setReq(req);
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
        TResp resp = new TResp();
        resp.setCode(code);
        resp.setMsg(errMsg);
        return resp;
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

}
