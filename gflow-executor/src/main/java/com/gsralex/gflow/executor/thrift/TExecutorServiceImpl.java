package com.gsralex.gflow.executor.thrift;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.TExecutorService;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.core.thriftgen.TResult;
import com.gsralex.gflow.executor.ExecuteProcess;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.executor.ExecutorThread;
import org.apache.thrift.TException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class TExecutorServiceImpl implements TExecutorService.Iface {


    private GFlowContext context;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private TExecutorClient client;


    public TExecutorServiceImpl(GFlowContext context) {
        this.context = context;
        this.client = new TExecutorClient(context);
    }

    @Override
    public TResult schedule(TJobDesc desc) {
        Parameter parameter = new Parameter(desc.getParameter());
        try {
            Class type = Class.forName(desc.getClassName());
            ExecuteProcess process;
            if (ExecutorContext.getContext().containsBean(type)) {
                process = (ExecuteProcess) ExecutorContext.getContext().getSpringBean(type);
            } else {
                process = (ExecuteProcess) type.newInstance();
            }
            ExecutorThread thread = new ExecutorThread(process, client);
            thread.setTJobDesc(desc);
            thread.setParameter(parameter);
            executorService.execute(thread);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        TResult tResult = new TResult();
        tResult.setOk(true);
        return tResult;
    }

    @Override
    public TResult heartbeat() throws TException {
        return new TResult(true, "heartbeat ok");
    }
}
