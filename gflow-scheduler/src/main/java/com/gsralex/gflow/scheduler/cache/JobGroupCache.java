package com.gsralex.gflow.scheduler.cache;

import com.gsralex.gflow.core.domain.GFlowExecution;
import com.gsralex.gflow.scheduler.context.GFlowContext;
import com.gsralex.gflow.scheduler.dao.JobExecDao;
import com.gsralex.gflow.scheduler.dao.impl.JobExecDaoImpl;

import java.util.List;

/**
 * @author gsralex
 * @date 2018/3/4
 */
public class JobGroupCache {

    private static List<GFlowExecution> flowExecutionList;


    private JobExecDao jobExecDao;

    public JobGroupCache(GFlowContext context) {
        this.jobExecDao = context.getBeanMap().getBean(JobExecDao.class);
    }

    public void reload() {
        flowExecutionList = jobExecDao.getExecutionList();
    }


    public static void main(String[] args) {
        GFlowContext context = new GFlowContext();
        context.init();

        JobExecDao dao = new JobExecDaoImpl(context);
        context.getBeanMap().setBean(JobExecDao.class, dao);
        JobGroupCache cache = new JobGroupCache(context);
        cache.reload();


    }
}
