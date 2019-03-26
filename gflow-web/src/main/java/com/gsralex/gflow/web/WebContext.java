package com.gsralex.gflow.web;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.client.RpcClientManager;
import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.gflow.web.configuration.WebConfig;

import java.io.IOException;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public class WebContext {

    private static final String CONFIG_FILEPATH = "/gflow.properties";

    private static final WebContext CURRENT = new WebContext();

    private WebContext() {
    }

    public static WebContext getInstance() {
        return CURRENT;
    }

    private WebConfig webConfig;

    public WebConfig getConfig() {
        return webConfig;
    }

    private RpcClientManager schedulerIpManager;

    public void init() throws IOException {
        webConfig = PropertiesUtils.getConfig(CONFIG_FILEPATH, WebConfig.class);
        List<IpAddr> schedulerIps = IpAddr.getIpsByConfig(webConfig.getSchedulerIps());
        schedulerIpManager = new RpcClientManager();
        schedulerIpManager.updateServerNodes(schedulerIps);
    }

    public RpcClientManager getSchedulerIpManager() {
        return schedulerIpManager;
    }
}
