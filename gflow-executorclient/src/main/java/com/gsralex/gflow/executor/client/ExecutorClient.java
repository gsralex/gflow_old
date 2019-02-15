package com.gsralex.gflow.executor.client;


import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.executor.client.action.JobReq;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @version 2019/2/8
 */
public interface ExecutorClient {

    /**
     * 调度执行
     * @param req
     * @return
     */
    Resp schedule(JobReq req) throws TException;

}
