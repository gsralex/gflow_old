package com.gsralex.gflow.scheduler;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.util.PropertiesUtils;
import com.gsralex.gflow.pub.util.SecurityUtils;
import com.gsralex.gflow.scheduler.config.SchedulerConfig;
import com.gsralex.gflow.scheduler.flow.FlowGuideMap;
import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.parameter.DynamicParamContext;
import com.gsralex.gflow.scheduler.timer.TimerProcess;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
     * 当前主机名端口
     */
    private IpAddr myIp;


    private String accessToken;

    private HbContext hbContext;

    private TimerProcess timerProcess;

    public void init() throws IOException {
        InputStream is = PropertiesUtils.class.getResourceAsStream(CONFIG_FILEPATH);
        config = PropertiesUtils.getConfig(is, SchedulerConfig.class);
        initJdbc();
        //主ip
        masterIp = new IpAddr(config.getSchedulerMaster());

        InetAddress addr = InetAddress.getLocalHost();
        myIp = new IpAddr(addr.getHostAddress(), config.getPort());
    }

    private void initJdbc() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(config.getDbUrl());
        dataSource.setUsername(config.getDbUsername());
        dataSource.setPassword(config.getDbPassword());
        dataSource.setDriverClassName(config.getDbDriver());
        jdbcUtils = new JdbcUtils(dataSource);
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

    public void setMaster(boolean master) {
        this.master = master;
    }

    public IpAddr getMyIp() {
        return myIp;
    }

    public String getAccessToken() {
        return SecurityUtils.encrypt(this.getConfig().getAccessKey());
    }


    public HbContext getHbContext() {
        return hbContext;
    }

    public void setHbContext(HbContext hbContext) {
        this.hbContext = hbContext;
    }

    public TimerProcess getTimerProcess() {
        return timerProcess;
    }

    public void setTimerProcess(TimerProcess timerProcess) {
        this.timerProcess = timerProcess;
    }

    /**
     * 获取executor ip
     *
     * @param tag
     * @return
     */
    public IpAddr getExecutorIp(String tag) {
        if (master) {
            return hbContext.getmExecutorHbProcess().getOnlineIpSeq(tag);
        } else {
            return hbContext.getsExecutorHbProcess().getOnlineIpSeq(tag);
        }
    }


    public IpAddr getMasterIp() {
        return masterIp;
    }

    public void setMasterIp(IpAddr masterIp) {
        this.masterIp = masterIp;
    }


    public static void main(String[] args) throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr);
    }
}
