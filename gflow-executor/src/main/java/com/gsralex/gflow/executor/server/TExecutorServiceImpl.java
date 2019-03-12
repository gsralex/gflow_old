package com.gsralex.gflow.executor.server;

import com.gsralex.gflow.executor.*;
import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.Parameter;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.scheduler.TExecutorService;
import com.gsralex.gflow.pub.thriftgen.scheduler.TJobReq;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorServiceImpl implements TExecutorService.Iface {


    private static final Logger LOG = Logger.getLogger(TExecutorServiceImpl.class);
    private ExecutorService executorService;

    private ExecutorContext context;

    public TExecutorServiceImpl(ExecutorContext context) {
        this.context = context;
        this.executorService = Executors.newFixedThreadPool(context.getConfig().getThreads());
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
                ExecutorThread thread = new ExecutorThread(process, this.context, context.getSchedulerIpManager().getIp());
                thread.setReq(req);
                thread.setParameter(parameter);
                executorService.execute(thread);
                code = ErrConstants.OK;
            } else if (AckExecuteProcess.class.isAssignableFrom(type)) {
                AckExecuteProcess process = (AckExecuteProcess) getInstance(type);
                AckExecutorThread thread = new AckExecutorThread(process, context);
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
            LOG.error("TExecutorServiceImpl.schedule jobid:" + req.getId() + "," +
                    " parameter:" + req.getParameter(), e);
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
