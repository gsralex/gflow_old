package com.gsralex.gflow.core.zk;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author gsralex
 * @version 2018/10/3
 */
public class SchedulerIpDataTest {
    @Test
    public void getIpsByConfig() throws Exception {
        GFlowContext context = GFlowContext.getContext();
        GFlowConfig config = new GFlowConfig();
        context.setConfig(config);
        config.setZkActive(false);
        config.setSchedulerIps("127.0.0.1:8080,127.0.0.1:8081");
        SchedulerIpData ipData = new SchedulerIpData(context);
        Assert.assertEquals(ipData.getIps().get(0).toString(), "127.0.0.1:8080");
        Assert.assertEquals(ipData.getIps().get(1).toString(), "127.0.0.1:8081");
    }

    @Test
    public void getIpsByZk() throws Exception {
        GFlowContext context = GFlowContext.getContext();
        context.initConfig();
        context.initZk();
        List<IpAddress> ips = new ArrayList<>();
        ips.add(new IpAddress("127.0.0.1", 8080));
        ips.add(new IpAddress("127.0.0.1", 8081));
        ZkIpData zkExecutor = new ZkIpData(context.getZkContext(), ZkConstants.ZKPATH_SCHEDULER_IP);
        zkExecutor.writeIps(ips);
        GFlowConfig config = context.getConfig();
        config.setZkActive(true);
        SchedulerIpData ipData = new SchedulerIpData(context);
        zkExecutor.writeIps(ips);
        Assert.assertEquals(ipData.getIps().get(0).toString(), "127.0.0.1:8080");
        Assert.assertEquals(ipData.getIps().get(1).toString(), "127.0.0.1:8081");
    }

}