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
    /**
     * 如果不设置任务超时时间，则重试时间应该完全覆盖任务超时时间
     */
    @PropertyName(name = "gflow.scheduler.retryintervalmills")
    private Long retryIntervalMills;
    @PropertyName(name = "gflow.scheduler.ips")
    private String schedulerIps;
    /**
     * 调度重试是否开启
     */
    @PropertyName(name = "gflow.scheduler.retryactive")
    private Boolean retryActive;
    /**
     * 任务执行超时时间，比如一个任务正常运行时10分钟，那么可以设定任务超时时间为11-15分钟，当超过超时时间，
     * 任务依然没有返回的时候，则理解为失败
     */
    @PropertyName(name = "gflow.executer.jobtimeout")
    private Long jobTimeout;
    @PropertyName(name = "gflow.executer.ips")
    private String executorIps;
    @PropertyName(name = "gflow.executor.threads")
    private Integer executorThreads;
    @PropertyName(name = "gflow.zk.active")
    private Boolean zkActive;
    @PropertyName(name = "gflow.accesskey")
    private String accessKey;

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

    public Long getRetryIntervalMills() {
        return retryIntervalMills;
    }

    public void setRetryIntervalMills(Long retryIntervalMills) {
        this.retryIntervalMills = retryIntervalMills;
    }

    public String getSchedulerIps() {
        return schedulerIps;
    }

    public void setSchedulerIps(String schedulerIps) {
        this.schedulerIps = schedulerIps;
    }

    public Boolean getRetryActive() {
        return retryActive;
    }

    public void setRetryActive(Boolean retryActive) {
        this.retryActive = retryActive;
    }

    public Long getJobTimeout() {
        return jobTimeout;
    }

    public void setJobTimeout(Long jobTimeout) {
        this.jobTimeout = jobTimeout;
    }

    public String getExecutorIps() {
        return executorIps;
    }

    public void setExecutorIps(String executorIps) {
        this.executorIps = executorIps;
    }

    public Integer getExecutorThreads() {
        return executorThreads;
    }

    public void setExecutorThreads(Integer executorThreads) {
        this.executorThreads = executorThreads;
    }

    public Boolean getZkActive() {
        return zkActive;
    }

    public void setZkActive(Boolean zkActive) {
        this.zkActive = zkActive;
    }

    public static GFlowConfig getConfig(String path) {
        try {
            return PropertiesUtils.getConfig(path, GFlowConfig.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
