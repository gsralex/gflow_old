package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.executor.connectclient.TExecutorClient;
import org.apache.log4j.Logger;

/**
 * @author gsralex
 * @version 2018/8/5
 */
public class ExecutorThread implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ExecutorThread.class);
    private ExecuteProcess process;
    private AckExecuteProcess ackProcess;
    private Parameter parameter;
    private TJobReq req;
    private TExecutorClient client;

    public ExecutorThread(ExecuteProcess process) {
        this.process = process;
    }

    public ExecutorThread(AckExecuteProcess ackProcess) {
        this.ackProcess = ackProcess;
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
            boolean ok = false;
            try {
                ok = process.process(jobReq);
            } catch (Exception e) {
                LOGGER.error(process.getClass().getName() + ":" + e);
            }
            client.ack(jobReq.getJobId(), ok);
        } else if (ackProcess != null) {
            try {
                ackProcess.process(jobReq);
            } catch (Exception e) {
                LOGGER.error(ackProcess.getClass().getName() + ":" + e);
            }
        }
    }
}
