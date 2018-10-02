package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.zk.ZkListener;
import com.gsralex.gflow.executor.zk.ZkExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/9/29
 */
public class IpData {
    //调度者地址
    private List<IpAddress> scheduleIps = new ArrayList<>();
    private GFlowContext context;

    public IpData(GFlowContext context) {
        this.context = context;
        GFlowConfig config = context.getConfig();
        if (config.getZkActive() != null && config.getZkActive()) {
            //启用zk
            zk();
        } else {
            scheduleIps.addAll(IpAddress.getIpsByConfig(config.getSchedulerIps()));
        }
    }

    private void zk() {
        ZkExecutor listener = new ZkExecutor(context.getZkContext());
        listener.setScheduleIpListener(new ZkListener<List<IpAddress>>() {
            @Override
            public void subscribeListen(List<IpAddress> value) {
                copyData(value);
            }
        });
        copyData(listener.getIps());
    }

    private void copyData(List<IpAddress> value) {
        synchronized (scheduleIps) {
            scheduleIps = value;
        }
    }


    public List<IpAddress> getIps() {
        return scheduleIps;
    }
}
