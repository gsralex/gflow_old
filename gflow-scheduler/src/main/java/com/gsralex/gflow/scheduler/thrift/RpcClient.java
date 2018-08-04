package com.gsralex.gflow.scheduler.thrift;


import com.gsralex.gflow.scheduler.domain.job.JobResult;


/**
 * @author gsralex
 * @date 2018/2/18
 */
public interface RpcClient {

    JobResult schedule(JobDesc jobDesc);

}
