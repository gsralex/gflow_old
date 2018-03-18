package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.domain.result.JobResult;
import com.gsralex.gflow.scheduler.rpc.JobDesc;
import com.gsralex.gflow.scheduler.thrift.gen.TJobDesc;
import com.gsralex.gflow.scheduler.thrift.gen.TJobResult;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TModelConverter {

    public static TJobDesc convertTJobDesc(JobDesc jobDesc) {
        TJobDesc tJobDesc = new TJobDesc();
        tJobDesc.setClassName(jobDesc.getClassName());
        tJobDesc.setId(jobDesc.getId());
        tJobDesc.setParameter(jobDesc.getParameter());
        tJobDesc.setJobGroupId(jobDesc.getJobGroupId());
        tJobDesc.setActionId(jobDesc.getActionId());
        return tJobDesc;
    }

    public static JobResult convertJobResult(TJobResult tJobResult) {
        JobResult jobResult = new JobResult();
        jobResult.setOk(tJobResult.isOk());
        jobResult.setErrMsg(tJobResult.getErrmsg());
        return jobResult;
    }
}
