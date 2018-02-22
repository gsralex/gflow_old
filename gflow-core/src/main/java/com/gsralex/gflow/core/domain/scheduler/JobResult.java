package com.gsralex.gflow.core.domain.scheduler;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public class JobResult {

    private JobStatusEnum jobStatus;
    private String errMsg;

    public JobStatusEnum getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatusEnum jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
