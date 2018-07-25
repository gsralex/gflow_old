package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.context.GFlowContext;
import com.gsralex.gflow.scheduler.dao.FlowJobDao;
import com.gsralex.gflow.scheduler.domain.flow.GFlowJob;
import com.gsralex.gflow.scheduler.domain.flow.GFlowJobGroup;

/**
 * @author gsralex
 * @version 2018/5/12
 */
public class FlowJobDaoImpl implements FlowJobDao {

    private JdbcUtils jdbcUtils;

    public FlowJobDaoImpl(GFlowContext context) {
        this.jdbcUtils = context.getJdbcUtils();
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
    public GFlowJobGroup getJobGroupByExecute(long triggerGroupId, String date) {
        String sql = "select * from gflow_jobgroup where trigger_group_id=? and date=? " +
                "and execute_config_id>0";
        return jdbcUtils.queryForObject(sql, new Object[]{triggerGroupId, date}, GFlowJobGroup.class);
    }

    @Override
    public boolean saveJob(GFlowJob job) {
        return jdbcUtils.insert(job, true);
    }
}
