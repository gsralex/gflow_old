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
    private ExecutorProcess process;
    private TExecutorClient client;
    private Parameter parameter;
    private TJobDesc jobDesc;

    public ExecutorThread(ExecutorProcess process, TExecutorClient client) {
        this.process = process;
        this.client = client;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public void setTJobDesc(TJobDesc jobDesc) {
        this.jobDesc = jobDesc;
    }

    @Override
    public void run() {
        boolean ok = false;
        try {
            ok = process.process(jobDesc.getId(), parameter);
        } catch (Exception e) {
            logger.error("ExecutorThread.run" + jobDesc.getId());
        }
        client.ack(jobDesc.getJobId(), ok);
    }
}
