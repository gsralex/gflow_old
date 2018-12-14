package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
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
    private TJobDesc tJobDesc;
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

    public void setTJobDesc(TJobDesc tJobDesc) {
        this.tJobDesc = tJobDesc;
    }

    @Override
    public void run() {
        JobDesc jobDesc = new JobDesc(tJobDesc.getId(), parameter);
        if (process != null) {
            boolean ok = false;
            try {
                ok = process.process(jobDesc);
            } catch (Exception e) {
                LOGGER.error(process.getClass().getName() + ":" + e);
            }
            client.ack(jobDesc.getJobId(), ok);
        } else if (ackProcess != null) {
            try {
                ackProcess.process(jobDesc);
            } catch (Exception e) {
                LOGGER.error(ackProcess.getClass().getName() + ":" + e);
            }
        }
    }
}
