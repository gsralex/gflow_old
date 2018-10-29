package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.executor.thrift.TExecutorClient;
import org.apache.log4j.Logger;

/**
 * @author gsralex
 * @version 2018/8/5
 */
public class ExecutorThread implements Runnable {

    private static final Logger logger = Logger.getLogger(ExecutorThread.class);
    private ExecuteProcess process;
    private AckExecuteProcess ackProcess;
    private Parameter parameter;
    private TJobDesc jobDesc;

    public ExecutorThread(ExecuteProcess process) {
        this.process = process;
    }

    public ExecutorThread(AckExecuteProcess ackProcess) {
        this.ackProcess = ackProcess;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public void setTJobDesc(TJobDesc jobDesc) {
        this.jobDesc = jobDesc;
    }

    @Override
    public void run() {
        if (process != null) {
            boolean ok = false;
            try {
                ok = process.process(jobDesc.getId(), parameter);
            } catch (Exception e) {
                logger.error(process.getClass().getName() + ":" + e);
            }
            ExecutorContext.getContext().ack(jobDesc.getId(), ok);
        } else if (ackProcess != null) {
            try {
                ackProcess.process(jobDesc.getId(), parameter);
            } catch (Exception e) {
                logger.error(ackProcess.getClass().getName() + ":" + e);
            }
        }
    }
}
