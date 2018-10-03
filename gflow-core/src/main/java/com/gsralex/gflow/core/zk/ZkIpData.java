package com.gsralex.gflow.core.zk;

import com.gsralex.gflow.core.context.IpAddress;
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
public class ZkIpData {

    private ZkClient zkClient;
    private ZkListener<List<IpAddress>> ipListener;
    private String zkPath;

    public ZkIpData(ZkContext zkContext, String zkPath) {
        this.zkClient = zkContext.getZkClient();
        this.zkPath = zkPath;
    }

    public void setScheduleIpListener(ZkListener<List<IpAddress>> zkListener) {
        this.ipListener = zkListener;
        this.zkClient.subscribeDataChanges(this.zkPath, new IZkDataListener() {
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
        if (!zkClient.exists(zkPath)) {
            zkClient.create(zkPath, ips, CreateMode.PERSISTENT);
        } else {
            zkClient.writeData(zkPath, ips);
        }
    }


    public List<IpAddress> getIps() {
        List<IpAddress> ipList = new ArrayList<>();
        if (zkClient.exists(zkPath)) {
            String ips = zkClient.readData(zkPath);
            ipList.addAll(IpAddress.getIpsByConfig(ips));
        }
        return ipList;
    }
}
