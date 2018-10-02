package com.gsralex.gflow.executor.zk;

import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.zk.ZkContext;
import com.gsralex.gflow.core.zk.ZkListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/9/27
 */
public class ZkExecutor {

    private ZkClient zkClient;
    private ZkListener<List<IpAddress>> ipListener;

    public ZkExecutor(ZkContext zkContext) {
        this.zkClient = zkContext.getZkClient();
    }

    public void setScheduleIpListener(ZkListener<List<IpAddress>> zkListener) {
        this.ipListener = zkListener;
        this.zkClient.subscribeDataChanges(ZkConstants.ZKPATH_SCHEDULER_IP, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                if (ipListener != null) {
                    ipListener.subscribeListen(getIps());
                }
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (ipListener != null) {
                    ipListener.subscribeListen(getIps());
                }
            }
        });
    }

    public void writeIps(List<IpAddress> list) {
        String ips = "";
        for (IpAddress ip : list) {
            ips += ip.toString() + ",";
        }
        ips = StringUtils.removeEnd(ips, ",");
        String zkPath = ZkConstants.ZKPATH_SCHEDULER_IP;
        if (!zkClient.exists(zkPath)) {
            zkClient.create(zkPath, ips, CreateMode.PERSISTENT);
        } else {
            zkClient.writeData(zkPath, ips);
        }
    }


    public List<IpAddress> getIps() {
        String zkPath = ZkConstants.ZKPATH_SCHEDULER_IP;
        List<IpAddress> ipList = new ArrayList<>();
        if (zkClient.exists(zkPath)) {
            String ips = zkClient.readData(zkPath);
            ipList.addAll(IpAddress.getIpsByConfig(ips));
        }
        return ipList;
    }
}
