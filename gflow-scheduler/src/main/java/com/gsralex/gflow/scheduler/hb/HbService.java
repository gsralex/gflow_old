package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.util.SecurityUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.ExecutorHbReq;
import com.gsralex.gflow.scheduler.client.action.scheduler.SchedulerNodeResp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<IpAddr> executorHb(IpAddr ip, String tag) {
        if (context.isMaster()) {
            context.getHbContext().getMasterReceiveExecutorHb().heartBeat(ip, tag);
            return context.getSchedulerIpManager().listIp();
        } else {
            //转发master
            IpAddr masterIp = context.getMasterIp();
            SchedulerClient client = SchedulerClientFactory.createScheduler(masterIp, context.getAccessToken());
            ExecutorHbReq req = new ExecutorHbReq();
            req.setIp(ip.getIp());
            req.setPort(ip.getPort());
            req.setAccessToken(SecurityUtils.encrypt(context.getConfig().getAccessKey()));
            req.setTag(tag);
            SchedulerNodeResp resp = client.executorHb(req);
            if (resp.getCode() == ErrConstants.OK) {
                return resp.getNodeList();
            } else {
                return null;
            }
        }
    }


    /**
     * scheduler slave -> master
     *
     * @param ip
     * @return 返回从节点
     */
    public List<ExecutorNode> schedulerHb(IpAddr ip) {
        context.getHbContext().getMasterReceiveSchedulerHb().heartBeat(ip);
        return new ArrayList<>();
    }
}
