package com.gsralex.gflow.core.zk;

import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.model.ExecuteConfig;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/20
 */
public class ZkExecuteConfigListener implements ZkListener {

    private ZkClient zkClient;
    private ZkData zkData;

    public ZkExecuteConfigListener(ZkClient zkClient, ZkData zkData) {
        this.zkClient = zkClient;
        this.zkData = zkData;
    }

    @Override
    public void subscribeListen() {
        //加入watch
        zkClient.subscribeChildChanges(ZkConstants.ZKPATH_EXECUTECONFIG, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                updateExecuteConfig();
            }
        });
    }

    private void updateExecuteConfig() {
        List<ExecuteConfig> executeConfigList = new ArrayList<>();
        String zkPath = ZkConstants.ZKPATH_EXECUTECONFIG;
        if (zkClient.exists(zkPath)) {
            List<String> pathList = zkClient.getChildren(zkPath);
            for (String path : pathList) {
                ExecuteConfig executeConfig = zkClient.readData(zkPath + "/" + path);
                executeConfigList.add(executeConfig);
            }
        }
        synchronized (zkData.getExecuteConfigList()) {
            zkData.setExecuteConfigList(executeConfigList);
        }
    }
}
