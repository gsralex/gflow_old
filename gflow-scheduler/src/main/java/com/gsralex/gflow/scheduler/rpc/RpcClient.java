package com.gsralex.gflow.scheduler.rpc;

import com.gsralex.gflow.core.domain.result.JobResult;

/**
 * @author gsralex
 * @date 2018/2/18
 */
public interface RpcClient {

    JobResult schedule(JobDesc jobDesc);

}
