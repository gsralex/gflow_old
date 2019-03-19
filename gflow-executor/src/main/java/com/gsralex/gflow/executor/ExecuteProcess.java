package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.client.JobReq;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public interface ExecuteProcess {

    boolean process(JobReq req) throws Exception;
}
