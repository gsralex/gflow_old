package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.rpc.client.RpcClientFactory;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.TimerService;
import com.gsralex.gflow.scheduler.client.domain.TimerReq;
import com.gsralex.gflow.scheduler.service.BizTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gsralex
 * @version 2018/12/18
 */
@Component
public class TimerServiceImpl implements TimerService {

    @Autowired
    private BizTimerService timerService;
    private SchedulerContext context = SchedulerContext.getInstance();

    @Override
    public boolean addTimer(TimerReq req) {
        if (!context.isMaster()) {
            TimerService timerService = RpcClientFactory.create(TimerService.class, context.getSchedulerIpManager(),
                    context.getMasterIp());
            return timerService.addTimer(req);
        }
        return timerService.saveTimer(req.getGroupId(), req.getActive(), req.getTime());
    }

    @Override
    public boolean updateTimer(TimerReq req) {
        if (!context.isMaster()) {
            TimerService timerService = RpcClientFactory.create(TimerService.class, context.getSchedulerIpManager(),
                    context.getMasterIp());
            return timerService.updateTimer(req);
        }
        return timerService.updateTimer(req.getId(), req.getGroupId(), req.getActive(), req.getTime());
    }

    @Override
    public boolean removeTimer(long id) {
        if (!context.isMaster()) {
            TimerService timerService = RpcClientFactory.create(TimerService.class, context.getSchedulerIpManager(),
                    context.getMasterIp());
            return timerService.removeTimer(id);
        }
        return timerService.removeTimer(id);
    }
}