package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.config.ExecutorConfig;
import com.gsralex.gflow.executor.spring.SpringContextHolder;
import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.util.IpManager;
import com.gsralex.gflow.pub.util.PropertiesUtils;
import com.gsralex.gflow.scheduler.client.SchedulerClient;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.action.scheduler.AckReq;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class ExecutorContext {

    private static final String CONFIG_FILEPATH = "/gflow.properties";

    private static final ExecutorContext current = new ExecutorContext();
    private boolean spring;

    private ExecutorConfig config;

    private IpAddr myIp;

    private IpManager schedulerIpManager;

    private ExecutorContext() {
    }

    public static ExecutorContext getInstance() {
        return current;
    }

    public void init() throws IOException {
        config = PropertiesUtils.getConfig(CONFIG_FILEPATH, ExecutorConfig.class);
        InetAddress addr = InetAddress.getLocalHost();
        this.myIp = new IpAddr(addr.getHostAddress(), config.getPort());

        //初始化ip轮询
        String[] ips = config.getSchedulerIps().split(",");
        List<IpAddr> ipList = new ArrayList<>();
        for (String ip : ips) {
            ipList.add(new IpAddr(ip));
        }
        schedulerIpManager = new IpManager(ipList);
    }

    public <T> T getSpringBean(Class<T> type) {
        return SpringContextHolder.getBean(type);
    }

    public void setSpringContext(ApplicationContext springContext) {
        SpringContextHolder.setApplicationContext(springContext);
        this.spring = true;
    }

    public Boolean isSpring() {
        return this.spring;
    }

    public IpAddr getMyIp() {
        return myIp;
    }

    public ExecutorConfig getConfig() {
        return config;
    }


    public IpManager getSchedulerIpManager() {
        return schedulerIpManager;
    }

    public boolean ack(long jobId, boolean ok) {
        SchedulerClient client = SchedulerClientFactory.createScheduler(schedulerIpManager.getIp(), "");
        AckReq req = new AckReq();
        req.setJobId(jobId);
        req.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        Resp resp = client.ack(req);
        return resp.getCode() == ErrConstants.OK ? true : false;
    }
}
