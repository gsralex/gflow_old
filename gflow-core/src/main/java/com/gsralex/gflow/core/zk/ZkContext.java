package com.gsralex.gflow.core.zk;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.model.ExecuteConfig;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/21
 */
public class ZkContext {

    private ZkClient zkClient;
    public ZkContext(GFlowConfig config) {
        zkClient = new ZkClient(config.getZkServer());
    }

    public ZkClient getZkClient() {
        return zkClient;
    }


}
