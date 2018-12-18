package com.gsralex.gflow.scheduler.domain;


import com.gsralex.gdata.bean.annotation.Column;
import com.gsralex.gdata.bean.annotation.Id;
import com.gsralex.gdata.bean.annotation.Table;

/**
 * @author gsralex
 * @date 2018/2/17
 */
@Table("gflow_timerconfig")
public class TimerConfig {

    @Id
    private Long id;
    @Column("flow_group_id")
    private Long flowGroupId;
    @Column("time_type")
    private Integer timeType;
    private String time;
    private Boolean active;
    @Column("create_time")
    private Long createTime;
    @Column("mod_time")
    private Long modTime;
    private Boolean del;
    @Column("parameter")
    private String parameter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlowGroupId() {
        return flowGroupId;
    }

    public void setFlowGroupId(Long flowGroupId) {
        this.flowGroupId = flowGroupId;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
