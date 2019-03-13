package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.constants.TimeConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.Node;
import com.gsralex.gflow.scheduler.client.action.scheduler.NodeResp;
import com.gsralex.gflow.scheduler.client.action.scheduler.ScheduleHbReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/3/13
 */
public class SlaveSchedulerHbProcess {

    private SlaveSchedulerHb hb;

    public SlaveSchedulerHbProcess(SchedulerContext context) {
        hb = new SlaveSchedulerHb(context);
    }

    public void start() {
        Thread t = new Thread(hb);
        t.start();
    }

    public void stop() {
        hb.stop();
    }

    public static class SlaveSchedulerHb implements Runnable {

        private static final Logger LOG = LoggerFactory.getLogger(SlaveSchedulerHb.class);
        private SchedulerContext context;
        private boolean interrupt;

        public SlaveSchedulerHb(SchedulerContext context) {
            this.context = context;
        }


        public void stop() {
            interrupt = false;
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

        @Override
        public void run() {
            while (!interrupt) {
                try {
                    IpAddr masterIp = context.getMasterIp();
                    SchedulerClient client = SchedulerClientFactory.createScheduler(masterIp, context.getAccessToken());
                    ScheduleHbReq req = new ScheduleHbReq();
                    IpAddr myIp = context.getMyIp();
                    req.setIp(myIp.getIp());
                    req.setPort(myIp.getPort());
                    NodeResp resp = client.schedulerHb(req);
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
    }
}
