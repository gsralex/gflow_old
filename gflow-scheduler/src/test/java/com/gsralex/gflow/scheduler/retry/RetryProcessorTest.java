package com.gsralex.gflow.scheduler.retry;

import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.context.GFlowContext;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author gsralex
 * @version 2018/10/8
 */
public class RetryProcessorTest {
    @Test
    public void getInterval() throws Exception {

        GFlowConfig config=new GFlowConfig();
        config.setRetryIntervalMills(10000L);//10秒
        GFlowContext.getContext().setConfig(config);
        RetryProcessor processor = new RetryProcessor();
        RetryTask task = new RetryTask();
        task.setRetryTime(System.currentTimeMillis());
        task.setRetryCnt(0);
        Assert.assertEquals(processor.getInterval(task), new Long(10000));
    }

}