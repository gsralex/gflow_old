package com.gsralex.gflow.core.config;


import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.gflow.core.util.PropertyName;

import java.io.IOException;

/**
 * @author gsralex
 * @version 2018/5/21
 */
public class GFlowConfig {

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
    @PropertyName(name = "gflow.port")
    private Integer port;

    @PropertyName(name = "gflow.scheduler.retrycnt")
    private Integer retryCnt;
    @PropertyName(name = "gflow.scheduler.retryinterval")
    private Long retryInterval;
    @PropertyName(name = "gflow.scheduler.ips")
    private String schedulerIps;
    @PropertyName(name = "gflow.executer.timeout")
    private Long jobTimeout;
    @PropertyName(name = "gflow.executer.ips")
    private String executorIps;
    @PropertyName(name = "gflow.zk.active")
    private Boolean zkActive;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getRetryCnt() {
        return retryCnt;
    }

    public void setRetryCnt(Integer retryCnt) {
        this.retryCnt = retryCnt;
    }

    public Long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Long retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Long getJobTimeout() {
        return jobTimeout;
    }

    public void setJobTimeout(Long jobTimeout) {
        this.jobTimeout = jobTimeout;
    }

    public static GFlowConfig getConfig(String path) {
        try {
            return PropertiesUtils.getConfig(path, GFlowConfig.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean getZkActive() {
        return zkActive;
    }

    public void setZkActive(Boolean zkActive) {
        this.zkActive = zkActive;
    }

    public String getSchedulerIps() {
        return schedulerIps;
    }

    public void setSchedulerIps(String schedulerIps) {
        this.schedulerIps = schedulerIps;
    }

    public String getExecutorIps() {
        return executorIps;
    }

    public void setExecutorIps(String executorIps) {
        this.executorIps = executorIps;
    }
}
