package com.gsralex.gflow.executor.client;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public interface ExecutorService {

    boolean scheduleAction(JobReq req);
}
