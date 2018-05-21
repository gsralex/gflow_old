package com.gsralex.gflow.scheduler.context;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/21
 */
public class ZkContext {

    private static final String ZKPATH_GFLOW_EXECUTORIP = "/gflow/executorip";

    private GFlowConfig config;

    private ZkClient zkClient;

    public ZkContext(GFlowConfig config) {
        this.config = config;

    }

    private void init() {
        String zkHost = "";
        //this.config.getZkHost();
        if (zkHost != null) {
            zkClient = new ZkClient(zkHost);
            zkClient.subscribeChildChanges(ZKPATH_GFLOW_EXECUTORIP, new ZkGflowExecutorIp());
        }
    }

    private static class ZkGflowExecutorIp implements IZkChildListener {
        @Override
        public void handleChildChange(String s, List<String> list) throws Exception {

        }
    }
}
