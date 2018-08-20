package com.gsralex.gflow.core.zk;

import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.context.IpAddress;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/20
 */
public class ZkIpListener implements ZkListener {

    private ZkClient zkClient;
    private ZkData zkData;

    public ZkIpListener(ZkClient zkClient, ZkData zkData) {
        this.zkClient = zkClient;
        this.zkData = zkData;
        initData();
        updateExecutorIp();
        updateScheduletorIp();
    }

    private void initData() {
        String path1 = ZkConstants.ZKPATH_EXECUTOR + "/ip1";
        if (zkClient.exists(path1)) {
            zkClient.delete(path1);
        }
        zkClient.create(path1, "localhost:10002", CreateMode.PERSISTENT);
        String path2 = ZkConstants.ZKPATH_SCHEDULER + "/ip1";
        if (zkClient.exists(path2)) {
            zkClient.delete(path2);
        }
        zkClient.create(path2, "localhost:20001", CreateMode.PERSISTENT);

    }

    @Override
    public void subscribeListen() {


        //加入watch
        zkClient.subscribeChildChanges(ZkConstants.ZKPATH_EXECUTOR, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("s:" + s + ",list:" + list);
                updateExecutorIp();
            }
        });

        zkClient.subscribeDataChanges(ZkConstants.ZKPATH_EXECUTOR, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("s:" + s + ",o:" + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("s:" + s);
            }
        });
        zkClient.subscribeChildChanges(ZkConstants.ZKPATH_SCHEDULER, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                updateScheduletorIp();
            }
        });
    }


    private void updateExecutorIp() {
        List<IpAddress> list = getIpList(ZkConstants.ZKPATH_EXECUTOR);
        synchronized (zkData.getExecutorIps()) {
            zkData.setExecutorIps(list);
        }

    }

    private void updateScheduletorIp() {
        List<IpAddress> list = getIpList(ZkConstants.ZKPATH_SCHEDULER);
        synchronized (zkData.getScheduleIps()) {
            zkData.setScheduleIps(list);
        }
    }

    private List<IpAddress> getIpList(String zkPath) {
        List<IpAddress> list = new ArrayList<>();
        if (zkClient.exists(zkPath)) {
            List<String> pathList = zkClient.getChildren(zkPath);
            for (String path : pathList) {
                String ip = zkClient.readData(zkPath + "/" + path);
                String[] ipArray = StringUtils.split(ip, ":");
                list.add(new IpAddress(ipArray[0], NumberUtils.toInt(ipArray[1])));
            }
        }
        return list;
    }

}
