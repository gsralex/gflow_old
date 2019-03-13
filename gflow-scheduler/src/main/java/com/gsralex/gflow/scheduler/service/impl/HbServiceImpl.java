package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.context.IpManager;
import com.gsralex.gflow.pub.util.SecurityUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.*;
import com.gsralex.gflow.scheduler.hb.ExecutorNode;
import com.gsralex.gflow.scheduler.hb.SchedulerNode;
import com.gsralex.gflow.scheduler.service.HbService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/2/13
 */
@Service
public class HbServiceImpl implements HbService {

    private SchedulerContext context = SchedulerContext.getInstance();

    /**
     * executor-> scheduler
     *
     * @param ip  ip
     * @param tag 标签
     * @return
     */
    @Override
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
            NodeResp resp = client.executorHb(req);
            if (resp.getCode() == ErrConstants.OK) {
                List<Node> nodeList = resp.getNodeList();
                List<IpAddr> schedulerIps = new ArrayList<>();
                for (Node node : nodeList) {
                    schedulerIps.add(node.getIp());
                }
                return schedulerIps;
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
    @Override
    public List<ExecutorNode> schedulerHb(IpAddr ip) {
        context.getHbContext().getMasterReceiveSchedulerHb().heartBeat(ip);
        List<ExecutorNode> list = new ArrayList<>();
        for (Map.Entry<String, IpManager> entry : context.getExecutorIpManager().entrySet()) {
            ExecutorNode node = new ExecutorNode();
            for (IpAddr executorIp : entry.getValue().listIp()) {
                node.setIp(executorIp);
                node.setTag(entry.getKey());
                list.add(node);
            }
        }
        return list;
    }

    @Override
    public List<SchedulerNode> listSchedulerNode() {
        if (context.isMaster()) {
            return context.getHbContext().getMasterReceiveSchedulerHb().listNode();
        } else {
            SchedulerClient client = SchedulerClientFactory.createScheduler(context.getMasterIp(), "");
            NodeStatusResp resp = client.listSchedulerNode();
            List<NodeStatus> nodeList = resp.getNodeList();
            List<SchedulerNode> scheNodeList = new ArrayList<>();
            for (NodeStatus node : nodeList) {
                SchedulerNode scheNode = new SchedulerNode();
                scheNode.setIp(node.getIp());
                scheNode.setOnline(node.isOnline());
                scheNodeList.add(scheNode);
            }
            return scheNodeList;
        }
    }

    @Override
    public List<ExecutorNode> listExecutorNode() {
        if (context.isMaster()) {
            return context.getHbContext().getMasterReceiveExecutorHb().listExecutorNode();
        } else {
            SchedulerClient client = SchedulerClientFactory.createScheduler(context.getMasterIp(), "");
            NodeStatusResp resp = client.listExecutorNode();
            List<NodeStatus> nodeList = resp.getNodeList();
            List<ExecutorNode> execNodeList = new ArrayList<>();
            for (NodeStatus node : nodeList) {
                ExecutorNode execNode = new ExecutorNode();
                execNode.setIp(node.getIp());
                execNode.setOnline(node.isOnline());
                execNodeList.add(execNode);
            }
            return execNodeList;
        }
    }
}
