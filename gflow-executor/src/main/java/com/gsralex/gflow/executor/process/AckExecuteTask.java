package com.gsralex.gflow.executor.process;

import com.gsralex.gflow.executor.AckExecuteProcess;
import com.gsralex.gflow.executor.client.JobReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2019/2/19
 */
public class AckExecuteTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ExecuteTask.class);
    private AckExecuteProcess process;
    private JobReq req;

    public AckExecuteTask(AckExecuteProcess process, JobReq req) {
        this.process = process;
        this.req = req;
    }

    @Override
    public void run() {
        try {
            process.process(req);
        } catch (Exception e) {
            LOG.error("AckExecuteTask.run", e);
        }
    }
}
