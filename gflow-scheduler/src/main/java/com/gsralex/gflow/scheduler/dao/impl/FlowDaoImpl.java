package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.pub.domain.Flow;
import com.gsralex.gflow.pub.domain.FlowDirect;
import com.gsralex.gflow.scheduler.dao.FlowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Repository
public class FlowDaoImpl implements FlowDao {

    @Autowired
    private JdbcUtils jdbcUtils;

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
