package com.gsralex.gflow.scheduler.recover;

import com.gsralex.gflow.scheduler.domain.JobGroup;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/24
 */

public class RecoverHandle {

    private FlowJobDao flowJobDao;
    private ScheduleLinkHandle scheduleLinkHandle;

    private void recoverTask() {
        List<JobGroup> groupList = flowJobDao.listJobGroupExecuting();
        for (JobGroup group : groupList) {
            scheduleLinkHandle.continueGroup(group.getId());
        }
    }
}
