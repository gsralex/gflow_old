package com.gsralex.gflow.scheduler.configuration;

import com.gsralex.gflow.pub.util.PropertyName;

public class SchedulerConfig {

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
    @PropertyName(name = "gflow.executer.ips")
    private String executorIps;
    @PropertyName(name = "gflow.scheduler.ips")
    private String schedulerIps;
    @PropertyName(name = "gflow.scheduler.masterip")
    private String schedulerMaster;
    @PropertyName(name = "gflow.scheduler.master")
    private Boolean master;

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

    public String getExecutorIps() {
        return executorIps;
    }

    public void setExecutorIps(String executorIps) {
        this.executorIps = executorIps;
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

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
