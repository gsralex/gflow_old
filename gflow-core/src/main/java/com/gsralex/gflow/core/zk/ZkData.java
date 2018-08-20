package com.gsralex.gflow.core.zk;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.model.ExecuteConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Zk数据集
 *
 * @author gsralex
 * @version 2018/8/20
 */
public class ZkData {

    /**
     * 执行服务器ip
     */
    private List<IpAddress> executorIps = new ArrayList<>();
    /**
     * 调度服务器ip
     */
    private List<IpAddress> scheduleIps = new ArrayList<>();

    private List<ExecuteConfig> executeConfigList = new ArrayList<>();

    public void setExecutorIps(List<IpAddress> executorIps) {
        this.executorIps = executorIps;
    }

    public void setScheduleIps(List<IpAddress> scheduleIps) {
        this.scheduleIps = scheduleIps;
    }

    public List<IpAddress> getExecutorIps() {
        return executorIps;
    }

    public List<IpAddress> getScheduleIps() {
        return scheduleIps;
    }

    public List<ExecuteConfig> getExecuteConfigList() {
        return executeConfigList;
    }

    public void setExecuteConfigList(List<ExecuteConfig> executeConfigList) {
        this.executeConfigList = executeConfigList;
    }
}
