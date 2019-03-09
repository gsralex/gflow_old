package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.util.SecurityUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.ExecutorHbReq;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2019/2/13
 */
@Service
public class HbService {

    private SchedulerContext context = SchedulerContext.getInstance();

    /**
     * executor-> scheduler
     *
     * @param ip  ip
     * @param tag 标签
     * @return
     */
    public boolean executorHb(IpAddr ip, String tag) {
        if (context.isMaster()) {
            context.getHbContext().getmExecutorHbProcess().heartBeat(ip, tag);
            return true;
        } else {
            //转发master
            IpAddr masterIp = context.getMasterIp();
            SchedulerClient client = SchedulerClientFactory.createScheduler(masterIp, context.getAccessToken());
            ExecutorHbReq req = new ExecutorHbReq();
            req.setIp(ip.getIp());
            req.setPort(ip.getPort());
            req.setAccessToken(SecurityUtils.encrypt(context.getConfig().getAccessKey()));
            req.setTag(tag);
            Resp resp = client.executorHb(req);
            if (resp.getCode() == ErrConstants.OK) {
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * scheduler slave -> master
     *
     * @param ip
     * @return
     */
    public boolean schedulerHb(IpAddr ip) {
        context.getHbContext().getmSchedulerHbProcess().heartBeat(ip);
        return true;
    }

    /**
     * scheduler master-> slave 更新executor(仅slave scheduler需要)
     *
     * @param ip
     * @param tag
     */
    public boolean updateExecutorNode(IpAddr ip, String tag, boolean online) {
        context.getHbContext().getsExecutorHbProcess().update(ip, tag, online);
        return true;
    }
}
