package com.gsralex.gflow.example.executor;

import com.gsralex.gflow.executor.ExecuteProcess;
import com.gsralex.gflow.executor.JobReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2019/3/11
 */
public class ExampleProcess implements ExecuteProcess {

    private static Logger LOG = LoggerFactory.getLogger(ExampleProcess.class);

    @Override
    public boolean process(JobReq desc) throws InterruptedException {
        LOG.info("class:" + this.getClass());
        LOG.info("jobId:" + desc.getJobId());
        LOG.info("parameter:" + desc.getParameter());
        //模拟流程
        Thread.sleep(1000 * 10);
        return true;
    }
}
