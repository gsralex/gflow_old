package com.gsralex.gflow.scheduler.cache;

import com.gsralex.gflow.core.domain.GFlowExecution;
import com.gsralex.gflow.scheduler.dao.JobExecDao;

import java.util.List;

/**
 * @author gsralex
 * @date 2018/3/4
 */
public class JobGroupCache {

    private static List<GFlowExecution> flowExecutionList;

    private JobExecDao jobExecDao;

    public void reload() {
        flowExecutionList = jobExecDao.getExecutionList();
    }
}
