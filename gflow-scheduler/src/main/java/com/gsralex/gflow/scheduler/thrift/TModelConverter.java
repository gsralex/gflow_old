package com.gsralex.gflow.scheduler.thrift;


import com.gsralex.gflow.scheduler.thrift.gen.TJobDesc;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class TModelConverter {

    public static TJobDesc convertTJobDesc(JobDesc jobDesc) {
        TJobDesc tJobDesc = new TJobDesc();
        tJobDesc.setId(jobDesc.getId());
        tJobDesc.setParameter(jobDesc.getParameter());
        tJobDesc.setJobGroupId(jobDesc.getJobGroupId());
        tJobDesc.setActionId(jobDesc.getActionId());
        return tJobDesc;
    }

}
