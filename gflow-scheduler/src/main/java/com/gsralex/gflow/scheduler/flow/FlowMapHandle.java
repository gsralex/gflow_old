package com.gsralex.gflow.scheduler.flow;

import com.gsralex.gflow.pub.domain.FlowItem;
import com.gsralex.gflow.pub.domain.FlowDirect;
import com.gsralex.gflow.pub.domain.Job;
import com.gsralex.gflow.pub.domain.JobGroup;
import com.gsralex.gflow.pub.enums.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.dao.ActionDao;
import com.gsralex.gflow.scheduler.dao.FlowDao;
import com.gsralex.gflow.scheduler.dao.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/19
 */

@Service
public class FlowMapHandle {


    @Autowired
    private ActionDao actionDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private FlowDao flowDao;

    private SchedulerContext context;


    public FlowGuide initGroup(long jobGroupId, long flowGroupId) {
        List<FlowItem> flowItemList = flowDao.listFlowItem(flowGroupId);
        List<FlowDirect> directList = flowDao.listFlowDirect(flowGroupId);
        FlowGuide flowGuide = new FlowGuide(jobGroupId, flowItemList, directList, JobGroupStatusEnum.EXECUTING);
        FlowGuideMap flowGuideMap = context.getFlowGuideMap();
        flowGuideMap.putFlowMap(jobGroupId, flowGuide);
        return flowGuide;
    }

    public FlowGuide getFlowGuide(long jobGroupId) {
        FlowGuideMap flowGuideMap = context.getFlowGuideMap();
        FlowGuide flowGuide = flowGuideMap.getFlowMap(jobGroupId);
        if (flowGuide == null) {
            JobGroup jobGroup = jobDao.getJobGroup(jobGroupId);
            if (jobGroup != null) {
                List<Job> jobList = jobDao.listJob(jobGroupId);
                List<FlowItem> flowItemList = flowDao.listFlowItem(jobGroupId);
                List<FlowDirect> directList = flowDao.listFlowDirect(jobGroupId);
                JobGroupStatusEnum status = JobGroupStatusEnum.valueOf(jobGroup.getStatus());
                flowGuide = new FlowGuide(jobGroupId, flowItemList, directList, jobList, status);
                flowGuideMap.putFlowMap(jobGroup.getId(), flowGuide);
                return flowGuide;
            } else {
                return null;
            }
        } else {
            return flowGuide;
        }
    }

}
