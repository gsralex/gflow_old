package com.gsralex.gflow.scheduler.flow;

import com.gsralex.gflow.core.domain.FlowDirectPo;
import com.gsralex.gflow.core.domain.FlowItemPo;
import com.gsralex.gflow.core.domain.JobPo;
import com.gsralex.gflow.core.domain.JobGroupPo;
import com.gsralex.gflow.core.enums.JobGroupStatus;
import com.gsralex.gflow.scheduler.SchedulerContext;
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
    private JobDao jobDao;
    @Autowired
    private FlowDao flowDao;

    private SchedulerContext context=SchedulerContext.getInstance();


    public FlowGuide initGroup(long jobGroupId, long flowGroupId) {
        List<FlowItemPo> flowItemPoList = flowDao.listFlowItem(flowGroupId);
        List<FlowDirectPo> directList = flowDao.listFlowDirect(flowGroupId);
        FlowGuide flowGuide = new FlowGuide(jobGroupId, flowItemPoList, directList, JobGroupStatus.EXECUTING);
        FlowGuideMap flowGuideMap = context.getFlowGuideMap();
        flowGuideMap.putFlowMap(jobGroupId, flowGuide);
        return flowGuide;
    }

    public FlowGuide getFlowGuide(long jobGroupId) {
        FlowGuideMap flowGuideMap = context.getFlowGuideMap();
        FlowGuide flowGuide = flowGuideMap.getFlowMap(jobGroupId);
        if (flowGuide == null) {
            JobGroupPo jobGroupPo = jobDao.getJobGroup(jobGroupId);
            if (jobGroupPo != null) {
                List<JobPo> jobPoList = jobDao.listJob(jobGroupId);
                List<FlowItemPo> flowItemPoList = flowDao.listFlowItem(jobGroupId);
                List<FlowDirectPo> directList = flowDao.listFlowDirect(jobGroupId);
                JobGroupStatus status = JobGroupStatus.valueOf(jobGroupPo.getStatus());
                flowGuide = new FlowGuide(jobGroupId, flowItemPoList, directList, jobPoList, status);
                flowGuideMap.putFlowMap(jobGroupPo.getId(), flowGuide);
                return flowGuide;
            } else {
                return null;
            }
        } else {
            return flowGuide;
        }
    }

}
