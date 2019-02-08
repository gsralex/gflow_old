package com.gsralex.gflow.scheduler.recover;

import com.gsralex.gflow.scheduler.domain.JobGroup;
import com.gsralex.gflow.scheduler.schedule.SchedulerService;
import com.gsralex.gflow.scheduler.dao.JobDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/24
 */

public class RecoverHandle {

    private JobDao jobDao;
    private SchedulerService schedulerService;

    private void recoverTask() {
        List<JobGroup> groupList = jobDao.listJobGroupExecuting();
        for (JobGroup group : groupList) {
            schedulerService.continueGroup(group.getId(), false);
        }
    }
}
