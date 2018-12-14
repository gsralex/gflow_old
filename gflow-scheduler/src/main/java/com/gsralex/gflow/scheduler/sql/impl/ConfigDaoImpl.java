package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.domain.*;
import com.gsralex.gflow.scheduler.sql.ConfigDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public class ConfigDaoImpl implements ConfigDao {

    private JdbcUtils jdbcUtils;

    public ConfigDaoImpl(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public Action getAction(long id) {
        String sql = "select * from gflow_action where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, Action.class);
    }

    @Override
    public List<ActionTag> listActionTag() {
        String sql = "select * from gflow_actiontag";
        return jdbcUtils.queryForList(sql, null, ActionTag.class);
    }

    @Override
    public List<ExecuteConfig> listExecuteConfig() {
        String sql = "select * from gflow_executeconfig";
        return jdbcUtils.queryForList(sql, null, ExecuteConfig.class);
    }

    @Override
    public List<Flow> listFlow(long groupId) {
        String sql = "select * from gflow_flow where action_group_id=?";
        return jdbcUtils.queryForList(sql, new Object[]{groupId}, Flow.class);
    }

    @Override
    public List<FlowDirect> listFlowDirect(long groupId) {
        String sql = "select * from gflow_flowdirect where group_id=?";
        return jdbcUtils.queryForList(sql, new Object[]{groupId}, FlowDirect.class);
    }

}
