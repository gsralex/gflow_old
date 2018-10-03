package com.gsralex.gflow.core.zk;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/10/3
 */
public class ExecutorIpData {
    private List<IpAddress> ips = new ArrayList<>();
    private GFlowContext context;

    public ExecutorIpData(GFlowContext context) {
        this.context = context;
        GFlowConfig config = context.getConfig();
        if (config.getZkActive() != null && config.getZkActive()) {
            //启用zk
            zk();
        } else {
            ips.addAll(IpAddress.getIpsByConfig(config.getExecutorIps()));
        }
    }

    private void zk() {
        ZkIpData listener = new ZkIpData(context.getZkContext(), ZkConstants.ZKPATH_EXECUTOR_IP);
        listener.setScheduleIpListener(new ZkListener<List<IpAddress>>() {
            @Override
            public void subscribeListen(List<IpAddress> value) {
                copyData(value);
            }
        });
        copyData(listener.getIps());
    }

    private void copyData(List<IpAddress> value) {
        synchronized (ips) {
            ips = value;
        }
    }


    public List<IpAddress> getIps() {
        return ips;
    }
}
