package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.constants.TimeConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.ExecutorNodeResp;
import com.gsralex.gflow.scheduler.client.action.scheduler.Node;
import com.gsralex.gflow.scheduler.client.action.scheduler.ScheduleHbReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * slave scheduler 心跳处理
 * 向master上报心跳
 *
 * @author gsralex
 * @version 2019/2/14
 */
public class SlaveSchedulerHb {

    private static final Logger LOG = LoggerFactory.getLogger(SlaveSchedulerHb.class);
    private SchedulerContext context;
    private boolean interrupt;

    public SlaveSchedulerHb(SchedulerContext context) {
        this.context = context;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLoop();
            }
        }).start();
    }

    public void stop() {
        interrupt = false;
    }

    private void mainLoop() {
        while (!interrupt) {
            try {
                IpAddr masterIp = context.getMasterIp();
                SchedulerClient client = SchedulerClientFactory.createScheduler(masterIp, context.getAccessToken());
                ScheduleHbReq req = new ScheduleHbReq();
                IpAddr myIp = context.getMyIp();
                req.setIp(myIp.getIp());
                req.setPort(myIp.getPort());
                ExecutorNodeResp resp = client.schedulerHb(req);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Salve Scheduler Heartbeat masterIp:{},myIp:{}", masterIp.toString(), myIp.toString());
                }
                setIps(resp.getNodeList());
                Thread.sleep(TimeConstants.HEARTBEAT_INTERVEL);
            } catch (Exception e) {
                LOG.error("SlaveSchedulerHb.mainLoop", e);
            }
        }
    }

    private void setIps(List<Node> nodeList) {
        Map<String, List<IpAddr>> ipMap = new HashMap<>();
        for (Node node : nodeList) {
            if (ipMap.containsKey(node.getTag())) {
                ipMap.get(node.getTag()).add(node.getIp());
            } else {
                List<IpAddr> list = new ArrayList<>();
                list.add(node.getIp());
                ipMap.put(node.getTag(), list);
            }
        }
        for (Map.Entry<String, List<IpAddr>> tagIp : ipMap.entrySet()) {
            context.setTagExecutorIp(tagIp.getKey(), tagIp.getValue());
        }
    }
}
