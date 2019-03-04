package com.gsralex.gflow.executor;

import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.context.Parameter;
import com.gsralex.gflow.pub.thriftgen.scheduler.TJobReq;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.AckReq;
import org.apache.log4j.Logger;

/**
 * @author gsralex
 * @version 2018/8/5
 */
public class ExecutorThread implements Runnable {

    private static final Logger LOG = Logger.getLogger(ExecutorThread.class);
    private ExecuteProcess process;
    private Parameter parameter;
    private TJobReq req;
    private ExecutorContext context;
    private IpAddr ip;

    public ExecutorThread(ExecuteProcess process, ExecutorContext context, IpAddr ip) {
        this.process = process;
        this.context = context;
        this.ip = ip;
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
                LOG.error(process.getClass().getName() + ":" + e);
                throw e;
            }
            SchedulerClient client = SchedulerClientFactory.createScheduler(ip, context.getAccessToken());
            AckReq req = new AckReq();
            req.setJobId(req.getJobId());
            req.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
            client.ack(req);
        }
    }
}
