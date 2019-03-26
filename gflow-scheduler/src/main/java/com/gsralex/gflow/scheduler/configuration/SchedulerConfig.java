package com.gsralex.gflow.scheduler.configuration;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.gflow.core.util.PropertyName;
import com.gsralex.gflow.scheduler.ExecutorNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SchedulerConfig {

    private static final String CONFIG_FILEPATH = "/gflow.properties";
    private static final String TAG_EXECUTORIP = "gflow.executor.tag";

    public static SchedulerConfig load() throws IOException {
        InputStream is = PropertiesUtils.class.getResourceAsStream(CONFIG_FILEPATH);
        Properties props = PropertiesUtils.getProperties(CONFIG_FILEPATH);
        SchedulerConfig config = PropertiesUtils.getConfig(props, SchedulerConfig.class);
        for (Map.Entry<Object, Object> propItem : props.entrySet()) {
            String key = propItem.getKey().toString();
            String value = propItem.getValue().toString();
            if (key.startsWith(TAG_EXECUTORIP)) {
                int index = key.lastIndexOf(".");
                String tag = key.substring(index + 1);
                String[] ips = value.split(",");
                List<IpAddr> ipList = new ArrayList<>();
                for (String ip : ips) {
                    ipList.add(new IpAddr(ip));
                }
                for (IpAddr ip : ipList) {
                    ExecutorNode node = new ExecutorNode();
                    node.setIp(ip);
                    node.setTag(tag);
                    config.getExecutorNodes().add(node);
                }
            }
        }
        return config;
    }


    @PropertyName(name = "gflow.db.driver")
    private String dbDriver;
    @PropertyName(name = "gflow.db.url")
    private String dbUrl;
    @PropertyName(name = "gflow.db.username")
    private String dbUsername;
    @PropertyName(name = "gflow.db.password")
    private String dbPassword;
    @PropertyName(name = "gflow.db.prefix")
    private String dbPrefix;
    @PropertyName(name = "gflow.zk.server")
    private String zkServer;
    @PropertyName(name = "gflow.zk.active")
    private Boolean zkActive;
    @PropertyName(name = "gflow.port")
    private Integer port;
    @PropertyName(name = "gflow.scheduler.ips")
    private String schedulerIps;
    @PropertyName(name = "gflow.scheduler.masterip")
    private String schedulerMaster;
    @PropertyName(name = "gflow.accesskey")
    private String accessKey;

    private Map<String, List<IpAddr>> executorIpMap = new HashMap<>();

    private List<ExecutorNode> executorNodes = new ArrayList<>();

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbPrefix() {
        return dbPrefix;
    }

    public void setDbPrefix(String dbPrefix) {
        this.dbPrefix = dbPrefix;
    }

    public String getZkServer() {
        return zkServer;
    }

    public void setZkServer(String zkServer) {
        this.zkServer = zkServer;
    }

    public Boolean getZkActive() {
        return zkActive;
    }

    public void setZkActive(Boolean zkActive) {
        this.zkActive = zkActive;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


    public String getSchedulerIps() {
        return schedulerIps;
    }

    public void setSchedulerIps(String schedulerIps) {
        this.schedulerIps = schedulerIps;
    }

    public String getSchedulerMaster() {
        return schedulerMaster;
    }

    public void setSchedulerMaster(String schedulerMaster) {
        this.schedulerMaster = schedulerMaster;
    }


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public List<ExecutorNode> getExecutorNodes() {
        return executorNodes;
    }
}
