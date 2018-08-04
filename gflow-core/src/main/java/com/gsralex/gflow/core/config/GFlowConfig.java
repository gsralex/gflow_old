package com.gsralex.gflow.core.config;


import com.gsralex.gflow.core.util.PropertyName;

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
}
