package com.gsralex.gflow.executor.registry;

import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.util.ZkUtils;
import com.gsralex.gflow.executor.ExecutorContext;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class ZkExecutorRegistry {

    private ExecutorContext context;
    private ZkClient zkClient;

    public ZkExecutorRegistry() {
        context = ExecutorContext.getInstance();
        zkClient = new ZkClient(context.getConfig().getZkServer());

    }

    public void register() {
        ZkUtils.createZNode(ZkConstants.ZKPATH_EXECUTOR_NODE,
                context.getConfig().getTag() + "_" + context.getMyIp().toString(),
                CreateMode.EPHEMERAL_SEQUENTIAL, zkClient);
    }

    public void subscribeScheduler() {
        zkClient.subscribeChildChanges(ZkConstants.ZKPATH_SCHEDULER, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                List<IpAddr> ipList = new ArrayList<>();
                for (String path : list) {
                    String node = zkClient.readData(path);
                    ipList.add(new IpAddr(node));
                }
                context.getSchedulerIpManager().updateServerNodes(ipList);
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
            nodeList.add(new IpAddr(ip));
        }
        return nodeList;
    }

    public static void main(String[] args) throws IOException {
        ExecutorContext.getInstance().init();
        ZkExecutorRegistry registry = new ZkExecutorRegistry();
        registry.register();
        registry.subscribeScheduler();
    }
}
