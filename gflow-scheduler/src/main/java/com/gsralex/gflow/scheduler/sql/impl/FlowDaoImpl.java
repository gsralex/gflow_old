package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.domain.Flow;
import com.gsralex.gflow.scheduler.domain.FlowDirect;
import com.gsralex.gflow.scheduler.sql.FlowDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/12/18
 */
public class FlowDaoImpl implements FlowDao {

    private JdbcUtils jdbcUtils;

    public FlowDaoImpl(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
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
