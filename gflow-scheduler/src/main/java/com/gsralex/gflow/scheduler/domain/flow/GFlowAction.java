package com.gsralex.gflow.scheduler.domain.flow;

/**
 * @author gsralex
 * @date 2017/12/25
 */
public class GFlowAction {
    private long id;
    private int type;
    private String name;
    private String className;
    private int createTime;
    private int modTime;
    private boolean sync;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public boolean getSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}
