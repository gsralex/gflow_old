package com.gsralex.gflow.scheduler.recover;

import com.gsralex.gflow.core.domain.JobGroup;
import com.gsralex.gflow.scheduler.schedule.ScheduleLinkHandle;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/24
 */
@Service
public class RecoverHandle {

    @Autowired
    private FlowJobDao flowJobDao;
    @Autowired
    private ScheduleLinkHandle scheduleLinkHandle;

    private void recoverTask() {
        List<JobGroup> groupList = flowJobDao.listJobGroupExecuting();
        for (JobGroup group : groupList) {
            scheduleLinkHandle.continueGroup(group.getId());
        }
    }
}
