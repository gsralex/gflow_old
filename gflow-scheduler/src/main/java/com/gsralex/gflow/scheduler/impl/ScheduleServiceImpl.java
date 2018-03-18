package com.gsralex.gflow.scheduler.impl;

import com.gsralex.gflow.core.dao.ActionDao;
import com.gsralex.gflow.core.domain.result.JobResult;
import com.gsralex.gflow.core.model.scheduler.ActionDeploy;
import com.gsralex.gflow.scheduler.ScheduleContext;
import com.gsralex.gflow.scheduler.ScheduleService;
import com.gsralex.gflow.scheduler.rpc.JobDesc;

import java.util.List;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public class ScheduleServiceImpl implements ScheduleService {


    private ScheduleContext context;
    private ActionDao actionDao;

    private ScheduleServiceImpl(ScheduleContext context) {
        this.context = context;
        actionDao = new ActionDao(this.context.getFlowContext());

    }

    @Override
    public JobResult submit(long actionId) {
        List<ActionDeploy> deployList = actionDao.getActionDeployList(actionId);
        ActionDeploy deploy = deployList.get(0);//简单处理，先取第一个
        //TODO:为null判断加入runtime error
        //TODO:加入重试机制
        JobDesc jobDesc = new JobDesc();
        jobDesc.setIp(deploy.getIp());
        jobDesc.setPort(deploy.getPort());
        jobDesc.setClassName(deploy.getClassName());
        jobDesc.setActionId(deploy.getActionId());
        return this.context.getRpcClient().schedule(jobDesc);
    }

    @Override
    public JobResult submitGroup(long triggerGroupId) {
        return null;
    }

    @Override
    public JobResult pauseJobGroup(long jobGroupId) {
        return null;
    }

    @Override
    public JobResult stopJobGroup(long jobGroupId) {
        return null;
    }
}
