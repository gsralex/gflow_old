package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import com.gsralex.gflow.core.enums.JobGroupStatusEnum;
import com.gsralex.gflow.core.enums.JobStatusEnum;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
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
    public boolean updateJobStatus(long id, int status) {
        String sql = "update gflow_job set status=? and mod_time=? where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{status, System.currentTimeMillis(), id}) != 0 ? true : false;
    }

    @Override
    public GFlowJob getJob(long id) {
        String sql = "select * from gflow_job where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, GFlowJob.class);
    }

    @Override
    public boolean incrJobRetryCnt(long id) {
        String sql = "update gflow_job set retry_cnt=retry_cnt+1 and mod_time=?  where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{System.currentTimeMillis(), id}) != 0 ? true : false;
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

    @Override
    public List<GFlowJob> listJobNeedRetry(int maxRetryCnt) {
        String sql = "select * from gflow_job where `status`<>? and retry_cnt<?";
        return jdbcUtils.queryForList(sql, new Object[]{JobStatusEnum.ExecuteOk, maxRetryCnt}, GFlowJob.class);
    }


}
