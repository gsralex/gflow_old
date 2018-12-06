package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.Parameter;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public class JobDesc {

    private Long jobId;
    private Parameter parameter;

    public JobDesc(long jobId, Parameter parameter) {
        this.jobId = jobId;
        this.parameter = parameter;
    }

    public Long getJobId() {
        return jobId;
    }

    public Parameter getParameter() {
        return parameter;
    }
}
