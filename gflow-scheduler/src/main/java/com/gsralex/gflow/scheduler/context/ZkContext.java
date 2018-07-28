package com.gsralex.gflow.scheduler.context;

import com.gsralex.gflow.scheduler.constant.ZkConstants;
import com.gsralex.gflow.scheduler.model.IpAddress;
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
    private List<IpAddress> ipList;


    public ZkContext(GFlowConfig config) {
        this.config = config;
        this.ipList=new ArrayList<>();

    }


    public void init() {
        if (this.config != null && !StringUtils.isEmpty(this.config.getZkServer())) {
            zkClient = new ZkClient(this.config.getZkServer());
            updateExecutorIp();
            //加入watch
            zkClient.subscribeChildChanges(ZkConstants.ZKPATH_EXECUTOR, new IZkChildListener() {
                @Override
                public void handleChildChange(String s, List<String> list) throws Exception {
                    updateExecutorIp();
                }
            });
        }
    }

    private void updateExecutorIp() {
       if(zkClient.exists(ZkConstants.ZKPATH_EXECUTOR)) {
           List<String> list = zkClient.getChildren(ZkConstants.ZKPATH_EXECUTOR);
           List<IpAddress> newIpList = new ArrayList<>();
           for (String path : list) {
               String ip = zkClient.readData(ZkConstants.ZKPATH_EXECUTOR + "/" + path);
               String[] ipArray = StringUtils.split(ip, ":");
               newIpList.add(new IpAddress(ipArray[0], NumberUtils.toInt(ipArray[1])));
           }
           synchronized (ipList) {
               ipList = newIpList;
           }
       }
    }
}
