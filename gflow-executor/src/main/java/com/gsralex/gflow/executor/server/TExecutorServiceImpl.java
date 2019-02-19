package com.gsralex.gflow.executor.server;

import com.gsralex.gflow.executor.*;
import com.gsralex.gflow.executor.hb.HbService;
import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.context.IpSeqSelector;
import com.gsralex.gflow.pub.context.Parameter;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.scheduler.TExecutorService;
import com.gsralex.gflow.pub.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.pub.thriftgen.scheduler.TScheduleHbReq;
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
    private HbService hbService;

    private ExecutorContext context;
    private IpSeqSelector ipSeqSelector;

    public TExecutorServiceImpl(ExecutorContext context) {
        this.context = context;
        int threads = 10;
        this.executorService = Executors.newFixedThreadPool(threads);
        this.hbService = new HbService(context);
        this.ipSeqSelector = new IpSeqSelector(context.getSchedulerHbProcess().listOnlineIp());
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
                this.ipSeqSelector.setIpList(context.getSchedulerHbProcess().listOnlineIp());
                ExecutorThread thread = new ExecutorThread(process, this.context, this.ipSeqSelector.getIp());
                thread.setReq(req);
                thread.setParameter(parameter);
                executorService.execute(thread);
                code = ErrConstants.OK;
            } else if (AsyncExecuteProcess.class.isAssignableFrom(type)) {
                AsyncExecuteProcess process = (AsyncExecuteProcess) getInstance(type);
                AsyncExecutorThread thread = new AsyncExecutorThread(process, context);
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
            LOG.error("TExecutorServiceImpl.schedule", e);
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


    @Override
    public TResp updateSchedulerNode(TScheduleHbReq req) throws TException {
        IpAddr ip = new IpAddr(req.getIp(), req.getPort());
        hbService.updateSchedulerNode(ip, true);
        TResp resp = new TResp();
        resp.setCode(ErrConstants.OK);
        return resp;
    }
}
