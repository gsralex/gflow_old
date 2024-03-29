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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class ExecutorServiceImpl implements ExecutorService {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutorServiceImpl.class);
    private ThreadPoolExecutor pool = new ThreadPoolExecutor(16, 32, 3600, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(65536));

    @Override
    public boolean scheduleAction(JobReq req) {
        try {
            Class<?> clazz = Class.forName(req.getClassName());
            Object instance;
            if (ExecutorContext.getInstance().isSpring()) {
                instance = ExecutorContext.getInstance().getSpringBean(clazz);
            } else {
                instance = clazz.newInstance();
            }
            if (instance != null) {
                if (ExecuteProcess.class.isInstance(instance)) {
                    ExecuteProcess process = (ExecuteProcess) instance;
                    pool.submit(new ExecuteTask(process, req));
                } else if (AckExecuteProcess.class.isInstance(instance)) {
                    AckExecuteProcess process = (AckExecuteProcess) instance;
                    pool.submit(new AckExecuteTask(process, req));
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            LOG.error("ExecutorServiceImpl.scheduleAction", e);
            return false;
        }
    }
}
