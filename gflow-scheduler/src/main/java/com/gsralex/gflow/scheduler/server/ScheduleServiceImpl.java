package com.gsralex.gflow.scheduler.server;


import com.gsralex.gflow.core.domain.JobGroupPo;
import com.gsralex.gflow.core.domain.JobPo;
import com.gsralex.gflow.core.rpc.client.RpcClientFactory;
import com.gsralex.gflow.core.rpc.client.RpcClientManager;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.ScheduleService;
import com.gsralex.gflow.scheduler.service.BizSchedulerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gsralex
 * @version 2018/3/18
 */
@Component
public class ScheduleServiceImpl implements ScheduleService {


    private SchedulerContext context = SchedulerContext.getInstance();
    @Autowired
    private BizSchedulerService schedulerService;

    @Override
    public boolean scheduleAction(long actionId, String parameter) {
        schedulerService.scheduleAction(actionId, parameter, false);
        return true;
    }

    @Override
    public boolean scheduleGroup(long flowGroupId, String parameter, long timerConfigId) {
        schedulerService.scheduleGroup(flowGroupId, parameter, timerConfigId, false);
        return true;
    }

    @Override
    public boolean ack(long jobId, boolean jobOk, String msg) {
        JobPo job = schedulerService.getJob(jobId);
        if (job != null) {
            if (job.getJobGroupId() != 0) {
                JobGroupPo jobGroup = schedulerService.getJobGroup(job.getJobGroupId());
                if (jobGroup != null) {
                    if (!StringUtils.equals(jobGroup.getStartServer(), context.getMyIp().toString())) {
                        RpcClientManager rpcClientManager = null;
                        ScheduleService service = RpcClientFactory.create(ScheduleService.class, rpcClientManager);
                        try {
                            service.ack(jobId, jobOk, msg);
                        } catch (Exception e) {
                            //如果跳转流转任务失败，则跳转master
                            if (context.isMaster()) {
                                return schedulerService.ackMaster(jobId, jobOk, msg, false);
                            } else {
                                if (!StringUtils.equals(jobGroup.getStartServer(), context.getMyIp().toString())) {
                                    service = RpcClientFactory.create(ScheduleService.class, rpcClientManager, context.getMasterIp());
                                    return service.ack(jobId, jobOk, msg);
                                }
                            }
                        }
                    } else {
                        return schedulerService.ackAction(jobId, jobOk, msg, false);
                    }
                }
            } else {
                return schedulerService.ackAction(jobId, jobOk, msg, false);
            }
        }
        return false;
    }
}
