package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.client.JobReq;

/**
 * @author gsralex
 * @version 2018/8/30
 */
public interface AckExecuteProcess {

    void process(JobReq req) throws Exception;
}
