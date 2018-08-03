package com.gsralex.gflow.scheduler.context;

import com.gsralex.gflow.scheduler.constant.ZkConstants;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ZkContextTest {
    @Before
    public void setUp() throws Exception {
        GFlowConfig config=new GFlowConfig();
        config.setZkServer("localhost:2181");

        ZkClient zkClient=new ZkClient(config.getZkServer());

        if(!zkClient.exists(ZkConstants.ZKPATH_EXECUTOR)) {
            zkClient.create(ZkConstants.ZKPATH_EXECUTOR, "ip", CreateMode.PERSISTENT);
        }
        String path1=ZkConstants.ZKPATH_EXECUTOR_IP+"1";
        if(!zkClient.exists(path1)){
            zkClient.create(path1,"localhost:2001", CreateMode.PERSISTENT);
        }
        String path2=ZkConstants.ZKPATH_EXECUTOR_IP+"2";
        if(!zkClient.exists(path2)){
            zkClient.create(path2,"localhost:2002", CreateMode.PERSISTENT);
        }
        String path3=ZkConstants.ZKPATH_EXECUTOR_IP+"3";
        if(!zkClient.exists(path3)) {
            zkClient.create(path3, "localhost:2003", CreateMode.PERSISTENT);
        }
    }

    @Test
    public void init() throws Exception {
        GFlowConfig config=new GFlowConfig();
        config.setZkServer("localhost:2181");
        ZkContext zkContext=new ZkContext(config);
        zkContext.init();
        System.in.read();
    }

}