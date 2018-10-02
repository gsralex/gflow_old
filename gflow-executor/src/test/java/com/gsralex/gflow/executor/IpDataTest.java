package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.executor.zk.ZkExecutor;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @author gsralex
 * @version 2018/10/2
 */
public class IpDataTest {

    @Test
    public void getIpsByConfig() throws Exception {
        GFlowContext context = GFlowContext.getContext();
        GFlowConfig config = new GFlowConfig();
        context.setConfig(config);
        config.setZkActive(false);
        config.setSchedulerIps("127.0.0.1:8080,127.0.0.1:8081");
        IpData ipData = new IpData(context);
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
        ZkExecutor zkExecutor = new ZkExecutor(context.getZkContext());
        zkExecutor.writeIps(ips);
        GFlowConfig config = context.getConfig();
        config.setZkActive(true);
        IpData ipData = new IpData(context);
        zkExecutor.writeIps(ips);
        Assert.assertEquals(ipData.getIps().get(0).toString(), "127.0.0.1:8080");
        Assert.assertEquals(ipData.getIps().get(1).toString(), "127.0.0.1:8081");
    }

}