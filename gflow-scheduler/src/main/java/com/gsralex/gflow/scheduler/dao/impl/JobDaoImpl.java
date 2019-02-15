package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.domain.Job;
import com.gsralex.gflow.scheduler.domain.JobGroup;
import com.gsralex.gflow.scheduler.enums.JobGroupStatusEnum;
import com.gsralex.gflow.scheduler.enums.JobStatusEnum;
import com.gsralex.gflow.scheduler.model.TimerExecuteRecord;
import com.gsralex.gflow.scheduler.dao.IdUtils;
import com.gsralex.gflow.scheduler.dao.JobDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/5/12
 */
public class JobDaoImpl implements JobDao {

    private JdbcUtils jdbcUtils;

    public JobDaoImpl(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public boolean saveJobGroup(JobGroup jobGroup) {
        return jdbcUtils.insert(jobGroup, true);
    }

    @Override
    public boolean updateJobGroup(JobGroup jobGroup) {
        return jdbcUtils.update(jobGroup);
    }

    @Override
    public JobGroup getJobGroup(long id) {
        String sql = "select * from gflow_jobgroup where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, JobGroup.class);
    }

    @Override
    public boolean saveJob(Job job) {
        return jdbcUtils.insert(job, true);
    }

    @Override
    public boolean updateJob(Job job) {
        return jdbcUtils.update(job);
    }

    @Override
    public boolean updateJobStatus(long id, int status) {
        String sql = "update gflow_job set status=? and mod_time=? where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{status, System.currentTimeMillis(), id}) != 0 ? true : false;
    }

    @Override
    public Job getJob(long id) {
        String sql = "select * from gflow_job where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, Job.class);
    }

    @Override
    public boolean incrJobRetryCnt(long id) {
        String sql = "update gflow_job set retry_cnt=retry_cnt+1 and mod_time=?  where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{System.currentTimeMillis(), id}) != 0 ? true : false;
    }

    @Override
    public List<JobGroup> listJobGroupExecuting() {
        String sql = "select * from gflow_jobgroup where status=?";
        return jdbcUtils.queryForList(sql, new Object[]{JobGroupStatusEnum.EXECUTING.getValue()}, JobGroup.class);
    }

    @Override
    public List<Job> listJob(long jobGroupId) {
        return null;
    }

    @Override
    public List<Job> listJobNeedRetry(int maxRetryCnt) {
        String sql = "select * from gflow_job where `status`<>? and retry_cnt<?";
        return jdbcUtils.queryForList(sql, new Object[]{JobStatusEnum.ExecuteOk, maxRetryCnt}, Job.class);
    }

    @Override
    public List<TimerExecuteRecord> listJobGroupExec(List<Long> timerIdList) {
        String sql="select max(create_time) as createtime,timer_config_id as timerconfigid from gflow_jobgroup  ";
        String ids= IdUtils.longToInts(timerIdList);
        sql+="where timer_config_id in ("+ids+")";
        sql+="group by timer_config_id";
        return jdbcUtils.queryForList(sql,null,TimerExecuteRecord.class);
    }


}
