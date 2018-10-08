package com.gsralex.gflow.executor.thrift;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.spring.SpringContextHolder;
import com.gsralex.gflow.core.thriftgen.TExecutorService;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.executor.ExecuteProcess;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.executor.ExecutorThread;
import com.gsralex.gflow.executor.demo.DemoProcess1;
import com.gsralex.gflow.executor.demo.SpringConfiguration;
import org.apache.thrift.TException;
import org.springframework.beans.BeansException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorServiceImpl implements TExecutorService.Iface {

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private TExecutorClient client;

    public TExecutorServiceImpl(ExecutorContext context) {
        this.client = new TExecutorClient(context);
    }

    @Override
    public TResult schedule(TJobDesc desc) {
        String errMsg = "";
        boolean ok = true;
        Parameter parameter = new Parameter(desc.getParameter());
        try {
            Class type = Class.forName(desc.getClassName());
            ExecuteProcess process;
            try {
                process = (ExecuteProcess) ExecutorContext.getContext().getSpringBean(type);
            } catch (BeansException e) {
                process = (ExecuteProcess) type.newInstance();
            }
            ExecutorThread thread = new ExecutorThread(process, client);
            thread.setTJobDesc(desc);
            thread.setParameter(parameter);
            executorService.execute(thread);
        } catch (Exception e) {
            errMsg = e.getMessage();
            ok = false;
        }
        TResult tResult = new TResult();
        tResult.setOk(ok);
        tResult.setErrmsg(errMsg);
        return tResult;
    }

    @Override
    public TResult heartbeat() throws TException {
        return new TResult(true, "heartbeat ok");
    }
}
