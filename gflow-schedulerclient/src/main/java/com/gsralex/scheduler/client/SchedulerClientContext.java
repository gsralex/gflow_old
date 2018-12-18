package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.scheduler.client.config.ClientConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/10/3
 */
public class SchedulerClientContext {

    private static final String CONFIG_FILEPATH = "/gflow.properties";
    private ClientConfig config;
    private List<IpAddress> schedulerIps = new ArrayList<>();

    private void init() throws IOException {
        config = PropertiesUtils.getConfig(CONFIG_FILEPATH, ClientConfig.class);
        initIps();
    }

    private void initIps() {
        String[] ips = config.getSchedulerIps().split(",");
        for (String ip : ips) {
            schedulerIps.add(IpAddress.getIp(ip));
        }
    }

    public List<IpAddress> getIps() {
        return schedulerIps;
    }

    public ClientConfig getConfig() {
        return config;
    }

    public static void main(String[] args) throws IOException {

        SchedulerClientContext context=new SchedulerClientContext();
        context.init();
        int id = Integer.parseInt(args[0]);
        Parameter parameter = new Parameter();
        parameter.put("bizdate", args[1]);
        ScheduleClient scheduleClient = new ScheduleClientImpl(context);
        scheduleClient.scheduleGroup(id, parameter);
    }
}
