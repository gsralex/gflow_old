package com.gsralex.gflow.scheduler.recover;

import com.gsralex.gflow.pub.domain.JobGroup;
import com.gsralex.gflow.scheduler.dao.JobDao;
import com.gsralex.gflow.scheduler.service.impl.SchedulerServiceImpl;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/24
 */

public class RecoverHandle {

    private JobDao jobDao;
    private SchedulerServiceImpl schedulerServiceImpl;

    private void recoverTask() {
        List<JobGroup> groupList = jobDao.listJobGroupExecuting();
        for (JobGroup group : groupList) {
            schedulerServiceImpl.continueGroup(group.getId(), false);
        }
    }
}
