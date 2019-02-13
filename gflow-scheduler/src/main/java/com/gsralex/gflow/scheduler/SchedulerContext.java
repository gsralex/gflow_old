package com.gsralex.gflow.scheduler;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.gflow.executor.client.ExecutorClient;
import com.gsralex.gflow.executor.client.ExecutorClientFactory;
import com.gsralex.gflow.scheduler.config.SchedulerConfig;
import com.gsralex.gflow.scheduler.flow.FlowGuideMap;
import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.parameter.DynamicParamContext;
import com.gsralex.gflow.scheduler.server.ServerStatus;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class SchedulerContext {

    private static final String CONFIG_FILEPATH = "/gflow.properties";

    private SchedulerConfig config;

    /**
     * 所有未完成的flow
     */
    private FlowGuideMap flowGuideMap = new FlowGuideMap();
    /**
     * 动态参数处理
     */
    private DynamicParamContext paramContext = DynamicParamContext.getContext();

    private JdbcUtils jdbcUtils;

    /**
     * 服务当前状态
     */
    private ServerStatus serverStatus;
    /**
     * 执行器ip
     */
    private List<IpAddress> executorIps = new ArrayList<>();

    /**
     * 服务器名+ip
     */
    private String myServer;

        private ExecutorClient client;


    private List<IpAddress> schedulerIps = new ArrayList<>();

    public void init() throws IOException {
        InputStream is = PropertiesUtils.class.getResourceAsStream(CONFIG_FILEPATH);
        config = PropertiesUtils.getConfig(is, SchedulerConfig.class);
        initJdbc();
        initIps();
        client = ExecutorClientFactory.create(new IpAddress("m1", 10001));
    }


    private void initJdbc() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(config.getDbUrl());
        dataSource.setUsername(config.getDbUsername());
        dataSource.setPassword(config.getDbPassword());
        dataSource.setDriverClassName(config.getDbDriver());
        jdbcUtils = new JdbcUtils(dataSource);
    }

    private void initIps() {
        String[] ips = config.getExecutorIps().split(",");
        for (String ip : ips) {
            String[] ipport = ip.split(":");
            IpAddress ipAddress = new IpAddress(ipport[0], Integer.parseInt(ipport[1]));
            executorIps.add(ipAddress);
        }
    }


    public List<IpAddress> getIps() {
        return executorIps;
    }

    public FlowGuideMap getFlowGuideMap() {
        return flowGuideMap;
    }

    public SchedulerConfig getConfig() {
        return config;
    }

    /**
     * 添加参数
     *
     * @param param
     */
    public void addParam(DynamicParam param) {
        this.paramContext.addParam(param);
    }

    public JdbcUtils getJdbcUtils() {
        return jdbcUtils;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public List<IpAddress> getSchedulerIps() {
        return schedulerIps;
    }

    public void setSchedulerIps(List<IpAddress> schedulerIps) {
        this.schedulerIps = schedulerIps;
    }

    public String getMyServer() {
        return myServer;
    }

    public void setMyServer(String myServer) {
        this.myServer = myServer;
    }

    public ExecutorClient getExecutorClient() {
        return client;
    }
}
