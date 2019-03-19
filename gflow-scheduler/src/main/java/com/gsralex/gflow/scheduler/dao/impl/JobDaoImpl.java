package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.domain.JobPo;
import com.gsralex.gflow.core.domain.JobGroupPo;
import com.gsralex.gflow.core.enums.JobGroupStatus;
import com.gsralex.gflow.core.enums.JobStatus;
import com.gsralex.gflow.scheduler.dao.IdUtils;
import com.gsralex.gflow.scheduler.dao.JobDao;
import com.gsralex.gflow.scheduler.dao.TimerExecuteRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Repository
public class JobDaoImpl implements JobDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public boolean saveJobGroup(JobGroupPo jobGroupPo) {
        return jdbcUtils.insert(jobGroupPo, true);
    }

    @Override
    public boolean updateJobGroup(JobGroupPo jobGroupPo) {
        return jdbcUtils.update(jobGroupPo);
    }

    @Override
    public JobGroupPo getJobGroup(long id) {
        String sql = "select * from gflow_jobgroup where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, JobGroupPo.class);
    }

    @Override
    public boolean saveJob(JobPo jobPo) {
        return jdbcUtils.insert(jobPo, true);
    }

    @Override
    public boolean updateJob(JobPo jobPo) {
        return jdbcUtils.update(jobPo);
    }

    @Override
    public boolean updateJobStatus(long id, int status) {
        String sql = "update gflow_job set status=? and mod_time=? where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{status, System.currentTimeMillis(), id}) != 0 ? true : false;
    }

    @Override
    public JobPo getJob(long id) {
        String sql = "select * from gflow_job where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, JobPo.class);
    }

    @Override
    public boolean incrJobRetryCnt(long id) {
        String sql = "update gflow_job set retry_cnt=retry_cnt+1 and mod_time=?  where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{System.currentTimeMillis(), id}) != 0 ? true : false;
    }

    @Override
    public List<JobGroupPo> listJobGroupExecuting() {
        String sql = "select * from gflow_jobgroup where status=?";
        return jdbcUtils.queryForList(sql, new Object[]{JobGroupStatus.EXECUTING.getValue()}, JobGroupPo.class);
    }

    @Override
    public List<JobPo> listJob(long jobGroupId) {
        return null;
    }

    @Override
    public List<JobPo> listJobNeedRetry(int maxRetryCnt) {
        String sql = "select * from gflow_job where `status`<>? and retry_cnt<?";
        return jdbcUtils.queryForList(sql, new Object[]{JobStatus.ExecuteOk, maxRetryCnt}, JobPo.class);
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
