package com.gsralex.gflow.scheduler.registry;

import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.util.ZkUtils;
import com.gsralex.gflow.scheduler.ExecutorNode;
import com.gsralex.gflow.scheduler.SchedulerContext;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class ZkSchedulerRegistry {

    private SchedulerContext context;
    private ZkClient zkClient;

    public ZkSchedulerRegistry() {
        context = SchedulerContext.getInstance();
        this.zkClient = new ZkClient(context.getConfig().getZkServer());
    }

    public void register() {
        ZkUtils.createZNode(ZkConstants.ZKPATH_SCHEDULER_NODE, context.getMyIp().toString(),
                CreateMode.EPHEMERAL_SEQUENTIAL, zkClient);
    }

    public void subscribeScheduler() {
        zkClient.subscribeChildChanges(ZkConstants.ZKPATH_SCHEDULER, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> paths) throws Exception {
                List<IpAddr> nodeList = listScheduler(paths);
                context.getSchedulerIpManager().updateServerNodes(nodeList);
            }
        });
    }

    public List<IpAddr> listScheduler() {
        List<String> paths = zkClient.getChildren(ZkConstants.ZKPATH_SCHEDULER);
        return listScheduler(paths);
    }

    private List<IpAddr> listScheduler(List<String> paths) {
        List<IpAddr> nodeList = new ArrayList<>();
        for (String path : paths) {
            String ip = zkClient.readData(ZkConstants.ZKPATH_SCHEDULER + "/" + path);
            IpAddr node = new IpAddr(ip);
            if (!context.getMyIp().equals(node)) {
                nodeList.add(node);
            }
        }
        return nodeList;
    }

    public void subscribeExecutor() {
        zkClient.subscribeChildChanges(ZkConstants.ZKPATH_EXECUTOR, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> paths) throws Exception {
                List<ExecutorNode> nodeList = listExecutor(paths);
                context.getExecutorClientManager().updateNodes(nodeList);
            }
        });
    }

    public List<ExecutorNode> listExecutor() {
        List<String> paths = zkClient.getChildren(ZkConstants.ZKPATH_EXECUTOR);
        return listExecutor(paths);
    }

    private List<ExecutorNode> listExecutor(List<String> paths) {
        List<ExecutorNode> nodeList = new ArrayList<>();
        for (String path : paths) {
            String node = zkClient.readData(ZkConstants.ZKPATH_EXECUTOR + "/" + path);
            String[] arr = node.split("_");
            String tag = arr[0];
            String ip = arr[1];
            ExecutorNode execNode = new ExecutorNode();
            execNode.setIp(new IpAddr(ip));
            execNode.setTag(tag);
            nodeList.add(execNode);
        }
        return nodeList;
    }

}
