package com.gsralex.gflow.executor;

/**
 * @author gsralex
 * @version 2018/8/30
 */
public interface AckExecuteProcess {

    void process(JobReq req) throws Exception;
}
