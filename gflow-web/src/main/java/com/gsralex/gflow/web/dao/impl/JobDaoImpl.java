package com.gsralex.gflow.web.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.web.dao.JobDao;
import com.gsralex.gflow.web.model.JobGroupVo;
import com.gsralex.gflow.web.model.JobVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public JobGroupVo getJobGroup(long id) {
        String sql = "select jobgroup.*,flowgroup.name,flowgroup.description from gflow_jobgroup as jobgroup left join gflow_flowgroup as flowgroup " +
                "on jobgroup.flow_group_id=flowgroup.id where jobgroup.id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, JobGroupVo.class);
    }

    @Override
    public List<JobGroupVo> listJobGroup(long flowGroupId, String date, int pageSize, int pageIndex) {
        String sql = "select jobgroup.*,flowgroup.name,flowgroup.description from gflow_jobgroup as jobgroup left join gflow_flowgroup as flowgroup " +
                "on jobgroup.flow_group_id=flowgroup.id where 1=1 ";

        List<Object> args = new ArrayList<>();
        if (flowGroupId != 0) {
            sql += "and jobgroup.flow_group_id=? ";
            args.add(flowGroupId);
        }

        if (!StringUtils.isEmpty(date)) {
            sql += "and jobgroup.date=? ";
            args.add(date);
        }
        int skip = (pageIndex - 1) * pageSize;
        sql += "order by jobgroup.id desc ";
        sql += "limit " + skip + "," + pageSize;
        return jdbcUtils.queryForList(sql, args.toArray(), JobGroupVo.class);
    }

    @Override
    public int countJobGroup(long flowGroupId, String date) {
        String sql = "select count(1) from gflow_jobgroup where 1=1 ";
        List<Object> args = new ArrayList<>();
        if (flowGroupId != 0) {
            sql += "and flow_group_id=? ";
            args.add(flowGroupId);
        }
        if (!StringUtils.isEmpty(date)) {
            sql += "and date=? ";
            args.add(date);
        }
        return jdbcUtils.queryForObject(sql, args.toArray(), Integer.class);
    }

    @Override
    public List<JobVo> listJob(long jobGroupId) {
        String sql = "select job.*,action.name,action.class_name " +
                "from gflow_job as job left join gflow_action as action " +
                "on job.action_id=action.id " +
                "where job.job_group_id=? order by job.id desc";
        return jdbcUtils.queryForList(sql, new Object[]{jobGroupId}, JobVo.class);
    }

    @Override
    public List<JobVo> listJob(long actionId, String date, int pageSize, int pageIndex) {
        String sql = "select job.*,action.name,action.class_name " +
                "from gflow_job as job left join gflow_action as action " +
                "on job.action_id=action.id where 1=1 ";
        List<Object> args = new ArrayList<>();
        if (actionId != 0) {
            sql += "and job.action_id=? ";
            args.add(actionId);
        }
        int skip = (pageIndex - 1) * pageSize;
        sql += "order by job.id desc limit " + skip + "," + pageSize;
        return jdbcUtils.queryForList(sql, args.toArray(), JobVo.class);
    }

    @Override
    public int countJob(long actionId, String date) {
        String sql = "select count(1) from gflow_job as job where 1=1 ";
        List<Object> args = new ArrayList<>();
        if (actionId != 0) {
            sql += "and job.action_id=? ";
            args.add(actionId);
        }
        return jdbcUtils.queryForObject(sql, args.toArray(), Integer.class);
    }
}
