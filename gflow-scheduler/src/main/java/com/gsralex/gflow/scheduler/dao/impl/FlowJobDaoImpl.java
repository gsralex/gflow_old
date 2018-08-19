package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.util.SqlUtils;
import com.gsralex.gflow.scheduler.dao.FlowJobDao;
import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/12
 */
public class FlowJobDaoImpl implements FlowJobDao {

    private JdbcUtils jdbcUtils;

    public FlowJobDaoImpl(GFlowContext context) {
        this.jdbcUtils = context.getJdbcContext().getJdbcUtils();
    }

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
    public int getJobGroupByExecute(long executeConfigId, int date) {
        String sql = "select count(1) from gflow_jobgroup where execute_config_id=? and date=?";
        return jdbcUtils.queryForObject(sql, new Object[]{executeConfigId, date}, Integer.class);
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
    public List<GFlowJob> getJobNeedRetryList(int[] statusArray, int retryCnt) {
        String status = SqlUtils.idToInt(statusArray);
        String sql = "select * from gflow_job where status in (" + status + ") and retrycnt<?";
        return jdbcUtils.queryForList(sql, new Object[]{retryCnt}, GFlowJob.class);
    }

    @Override
    public List<GFlowJob> listJob(long jobGroupId) {
        return null;
    }


}
