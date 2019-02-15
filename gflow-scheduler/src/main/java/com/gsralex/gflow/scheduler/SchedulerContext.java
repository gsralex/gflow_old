package com.gsralex.gflow.scheduler;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.util.PropertiesUtils;
import com.gsralex.gflow.pub.util.SecurityUtils;
import com.gsralex.gflow.scheduler.config.SchedulerConfig;
import com.gsralex.gflow.scheduler.flow.FlowGuideMap;
import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.parameter.DynamicParamContext;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
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
    private boolean master = false;

    private IpAddr masterIp;
    /**
     * 执行器ip
     */
    private List<IpAddr> executorIps = new ArrayList<>();
    /**
     * 当前主机名端口
     */
    private IpAddr myIp;
    private List<IpAddr> schedulerIps = new ArrayList<>();


    private String accessToken;

    private HbContext hbContext;

    public void init() throws IOException {
        InputStream is = PropertiesUtils.class.getResourceAsStream(CONFIG_FILEPATH);
        config = PropertiesUtils.getConfig(is, SchedulerConfig.class);
        initJdbc();
        initIps();
        //主ip
        masterIp = new IpAddr(config.getSchedulerMaster());

        InetAddress addr = InetAddress.getLocalHost();
        myIp = new IpAddr(addr.getHostAddress(), config.getPort());

        if (config.getSchedulerIps() != null) {
            String[] schedulerIps = config.getSchedulerIps().split(",");
            for (String ip : schedulerIps) {
                this.schedulerIps.add(new IpAddr(ip));
            }
        }
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
            IpAddr ipAddr = new IpAddr(ipport[0], Integer.parseInt(ipport[1]));
            executorIps.add(ipAddr);
        }
    }

    public List<IpAddr> getIps() {
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

    public boolean isMaster() {
        return master;
    }

    public void setSchedulerIps(List<IpAddr> schedulerIps) {
        this.schedulerIps = schedulerIps;
    }

    public IpAddr getMyIp() {
        return myIp;
    }

    public String getAccessToken() {
        return SecurityUtils.encrypt(this.getConfig().getAccessKey());
    }

    /**
     * 获取scheduler ip(仅master使用)
     *
     * @return
     */
    public List<IpAddr> getSchedulerIps() {
        return schedulerIps;
    }

    public HbContext getHbContext() {
        return hbContext;
    }

    public void setHbContext(HbContext hbContext) {
        this.hbContext = hbContext;
    }

    /**
     * 获取executor ip
     *
     * @param tag
     * @return
     */
    public List<IpAddr> getExecutorIps(String tag) {
        if (isMaster()) {
            return hbContext.getmExecutorHbProcess().listOnlineIp(tag);
        } else {
            return hbContext.getsExecutorHbProcess().listOnlineIp(tag);
        }
    }

    public IpAddr getMasterIp() {
        return masterIp;
    }

}
