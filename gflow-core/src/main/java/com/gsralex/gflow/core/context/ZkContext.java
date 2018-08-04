package com.gsralex.gflow.core.context;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.constants.ZkConstants;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/21
 */
public class ZkContext {

    private GFlowConfig config;
    private static ZkClient zkClient;
    private List<IpAddress> executorIps;
    private List<IpAddress> scheduleIps;


    public ZkContext(GFlowConfig config) {
        this.config = config;
        this.executorIps = new ArrayList<>();
        this.scheduleIps = new ArrayList<>();
        this.init();
    }

    public List<IpAddress> getExecutorIps() {
        return executorIps;
    }

    public List<IpAddress> getScheduleIps() {
        return scheduleIps;
    }

    public void init() {
        if (this.config != null && !StringUtils.isEmpty(this.config.getZkServer())) {
            zkClient = new ZkClient(this.config.getZkServer());
            updateExecutorIp();
            updateScheduletorIp();

            //加入watch
            zkClient.subscribeChildChanges(ZkConstants.ZKPATH_EXECUTOR, new IZkChildListener() {
                @Override
                public void handleChildChange(String s, List<String> list) throws Exception {
                    updateExecutorIp();
                }
            });
            zkClient.subscribeChildChanges(ZkConstants.ZKPATH_SCHEDULER, new IZkChildListener() {
                @Override
                public void handleChildChange(String s, List<String> list) throws Exception {
                    updateScheduletorIp();
                }
            });

        }
    }

    private void updateExecutorIp() {
        List<IpAddress> list = getIpList(ZkConstants.ZKPATH_EXECUTOR_IP);
        if (list.size() != 0) {
            synchronized (executorIps) {
                executorIps = list;
            }
        }
    }

    private void updateScheduletorIp() {
        List<IpAddress> list = getIpList(ZkConstants.ZKPATH_SCHEDULER_IP);
        if (list.size() != 0) {
            synchronized (scheduleIps) {
                scheduleIps = list;
            }
        }
    }

    private List<IpAddress> getIpList(String zkPath) {
        List<IpAddress> list = new ArrayList<>();
        if (zkClient.exists(ZkConstants.ZKPATH_SCHEDULER)) {
            List<String> pathList = zkClient.getChildren(ZkConstants.ZKPATH_EXECUTOR);
            for (String path : pathList) {
                String ip = zkClient.readData(ZkConstants.ZKPATH_EXECUTOR + "/" + path);
                String[] ipArray = StringUtils.split(ip, ":");
                list.add(new IpAddress(ipArray[0], NumberUtils.toInt(ipArray[1])));
            }
        }
        return list;
    }
}
