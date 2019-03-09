package com.gsralex.gflow.pub.domain;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public class ActionTag {
    private Long id;
    private String name;
    private String servers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }
}
