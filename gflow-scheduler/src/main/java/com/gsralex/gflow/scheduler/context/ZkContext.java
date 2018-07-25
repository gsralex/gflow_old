package com.gsralex.gflow.scheduler.context;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/21
 */
public class ZkContext {

    private static final String ZKPATH_GFLOW_EXECUTORIP = "/gflow/executor";

    private GFlowConfig config;

    private ZkClient zkClient;

    public ZkContext(GFlowConfig config) {
        this.config = config;

    }

    private void init() {
        String zkHost = "localhost:2181";
        //this.config.getZkHost();
        if (this.config != null && !StringUtils.isEmpty(this.config.getZkServer())) {
            zkClient = new ZkClient(this.config.getZkServer());
            zkClient.create(ZKPATH_GFLOW_EXECUTORIP,"mydata", CreateMode.EPHEMERAL);
            zkClient.subscribeChildChanges(ZKPATH_GFLOW_EXECUTORIP, new ZkGflowExecutorIp());
        }
    }

    public static void main(String[] args){
        GFlowConfig config=new GFlowConfig();
        config.setZkServer("localhost:2181");
        ZkContext zkContext=new ZkContext(config);
        zkContext.init();

    }

    private static class ZkGflowExecutorIp implements IZkChildListener {
        @Override
        public void handleChildChange(String s, List<String> list) throws Exception {

        }
    }
}
