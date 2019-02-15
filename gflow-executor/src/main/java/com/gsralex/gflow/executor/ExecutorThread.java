package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.core.util.IpSelector;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.AckReq;
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
    private ExecutorContext context;

    private IpSelector ipSelector;

    public ExecutorThread(ExecuteProcess process, ExecutorContext context) {
        this.process = process;
        this.ipSelector = new IpSelector(context.getScheduleIps());
        this.context = context;
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

            SchedulerClient client = SchedulerClientFactory.create(ipSelector.getIp(), context.getAccessToken());
            AckReq req = new AckReq();
            req.setJobId(req.getJobId());
            req.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
            client.ack(req);
        } else if (ackProcess != null) {
            try {
                ackProcess.process(jobReq);
            } catch (Exception e) {
                LOGGER.error(ackProcess.getClass().getName() + ":" + e);
            }
        }
    }
}
