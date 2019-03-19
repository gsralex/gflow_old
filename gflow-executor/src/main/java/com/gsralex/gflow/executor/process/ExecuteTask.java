package com.gsralex.gflow.executor.process;

import com.gsralex.gflow.core.rpc.client.RpcClientFactory;
import com.gsralex.gflow.executor.ExecuteProcess;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.executor.client.JobReq;
import com.gsralex.gflow.scheduler.client.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2018/8/5
 */
public class ExecuteTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ExecuteTask.class);
    private ExecuteProcess process;
    private JobReq req;
    public ExecuteTask(ExecuteProcess process, JobReq req) {
        this.process = process;
        this.req = req;
    }

    @Override
    public void run() {
        boolean ok = false;
        String msg = "";
        try {
            ok = process.process(req);
        } catch (Exception e) {
            LOG.error("ExecuteTask.run", e);
            msg = e.getMessage();
        }
        ScheduleService scheduleService = RpcClientFactory.create(ScheduleService.class, ExecutorContext.getInstance().getSchedulerIpManager());
        scheduleService.ack(req.getJobId(), ok, msg);
    }
}
