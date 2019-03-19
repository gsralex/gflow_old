package com.gsralex.gflow.executor.server;

import com.gsralex.gflow.executor.AckExecuteProcess;
import com.gsralex.gflow.executor.ExecuteProcess;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.executor.client.ExecutorService;
import com.gsralex.gflow.executor.client.JobReq;
import com.gsralex.gflow.executor.process.AckExecuteTask;
import com.gsralex.gflow.executor.process.ExecuteTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gsralex
 * @version 2019/3/16
 */
@Component
public class ExecutorServiceImpl implements ExecutorService {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutorServiceImpl.class);
    private ThreadPoolExecutor pool = new ThreadPoolExecutor(16, 32, 3600, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(65536));

    @Override
    public boolean scheduleAction(JobReq req) {
        try {
            Class<?> clazz = Class.forName(req.getClassName());
            if (ExecutorContext.getInstance().isSpring()) {
                Object instance = ExecutorContext.getInstance().getSpringBean(clazz);
                if (clazz.isInstance(ExecuteProcess.class)) {
                    ExecuteProcess process = (ExecuteProcess) instance;
                    pool.submit(new ExecuteTask(process, req));
                } else if (clazz.isInstance(AckExecuteProcess.class)) {
                    AckExecuteProcess process = (AckExecuteProcess) instance;
                    pool.submit(new AckExecuteTask(process, req));
                }
            }
            return true;
        } catch (Exception e) {
            LOG.error("ExecutorServiceImpl.scheduleAction", e);
            return false;
        }
    }

    @Override
    public boolean stopAction(long jobId) {
        return false;
    }
}
