package com.gsralex.gflow.core.domain;

/**
 * @author gsralex
 * @date 2018/2/13
 */
public class GFlowActionDeploy {

    private long id;
    private long actionId;
    private String deployName;
    private int createTime;
    private int modTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public String getDeployName() {
        return deployName;
    }

    public void setDeployName(String deployName) {
        this.deployName = deployName;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getModTime() {
        return modTime;
    }

    public void setModTime(int modTime) {
        this.modTime = modTime;
    }
}
