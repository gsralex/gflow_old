package com.gsralex.gflow.executor;

import com.gsralex.gflow.pub.context.Parameter;
import com.gsralex.gflow.pub.thriftgen.scheduler.TJobReq;
import org.apache.log4j.Logger;

/**
 * @author gsralex
 * @version 2019/2/19
 */
public class AsyncExecutorThread implements Runnable {

    private static final Logger LOG = Logger.getLogger(ExecutorThread.class);
    private AsyncExecuteProcess process;
    private Parameter parameter;
    private TJobReq req;
    private ExecutorContext context;

    public AsyncExecutorThread(AsyncExecuteProcess process, ExecutorContext context) {
        this.process = process;
        this.context = context;
    }


    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public void setReq(TJobReq req) {
        this.req = req;
    }

    @Override
    public void run() {
        JobReq jobReq = new JobReq(req.getId(), parameter);
        if (process != null) {
            try {
                process.process(jobReq);
            } catch (Exception e) {
                LOG.error(process.getClass().getName() + ":" + e);
                throw e;
            }
        }
    }
}
