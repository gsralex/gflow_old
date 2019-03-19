package com.gsralex.gflow.executor.client;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class JobReq {

    private long jobId;
    private String parameter;
    private String className;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
