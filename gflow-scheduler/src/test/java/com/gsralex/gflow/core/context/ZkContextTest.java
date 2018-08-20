package com.gsralex.gflow.core.context;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.zk.ZkContext;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

public class ZkContextTest {
    @Before
    public void setUp() throws Exception {
        GFlowConfig config=new GFlowConfig();
        config.setZkServer("m1:2181");

        ZkClient zkClient=new ZkClient(config.getZkServer());
        zkClient.writeData(ZkConstants.ZKPATH_EXECUTOR,"executor1");
    }

    @Test
    public void init() throws Exception {
        GFlowConfig config=new GFlowConfig();
        config.setZkServer("localhost:2181");
        ZkContext zkContext=new ZkContext(config);
        System.in.read();
    }

}