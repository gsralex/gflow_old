package com.gsralex.gflow.web;

import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.util.PropertiesUtils;
import com.gsralex.gflow.web.configuration.WebConfig;

import java.io.IOException;
import java.util.ArrayList;
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

    public void init() throws IOException {
        webConfig = PropertiesUtils.getConfig(CONFIG_FILEPATH, WebConfig.class);
        String[] ipArr = webConfig.getSchedulerIps().split(",");
        for (String ip : ipArr) {
            schedulerIps.add(new IpAddr(ip));
        }
    }


    private List<IpAddr> schedulerIps = new ArrayList<>();

    public List<IpAddr> getSchedulerIps() {
        return schedulerIps;
    }
}
