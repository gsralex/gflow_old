package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import com.gsralex.gflow.core.enums.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/12
 */
@Repository
public class FlowJobDaoImpl implements FlowJobDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public boolean saveJobGroup(GFlowJobGroup jobGroup) {
        return jdbcUtils.insert(jobGroup, true);
    }

    @Override
    public boolean updateJobGroup(GFlowJobGroup jobGroup) {
        return jdbcUtils.update(jobGroup);
    }

    @Override
    public GFlowJobGroup getJobGroup(long id) {
        String sql = "select * from gflow_jobgroup where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, GFlowJobGroup.class);
    }


    @Override
    public boolean saveJob(GFlowJob job) {
        return jdbcUtils.insert(job, true);
    }

    @Override
    public boolean updateJob(GFlowJob job) {
        return jdbcUtils.update(job);
    }

    @Override
    public GFlowJob getJob(long id) {
        String sql = "select * from gflow_job where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, GFlowJob.class);
    }

    @Override
    public int batchSaveJob(List<GFlowJob> jobList) {
        return jdbcUtils.batchInsert(jobList, true);
    }

    @Override
    public List<GFlowJobGroup> listJobGroupExecuting() {
        String sql = "select * from gflow_jobgroup where status=?";
        return jdbcUtils.queryForList(sql, new Object[]{JobGroupStatusEnum.EXECUTING.getValue()}, GFlowJobGroup.class);
    }

    @Override
    public List<GFlowJob> listJob(long jobGroupId) {
        return null;
    }


}
