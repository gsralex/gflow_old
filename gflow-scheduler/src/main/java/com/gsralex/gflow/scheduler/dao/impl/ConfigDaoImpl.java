package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.dao.ConfigDao;
import com.gsralex.gflow.scheduler.domain.flow.GFlowExecuteConfig;
import com.gsralex.gflow.scheduler.domain.flow.GFlowTrigger;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public class ConfigDaoImpl implements ConfigDao {

    private JdbcUtils jdbcUtils;


    @Override
    public List<GFlowExecuteConfig> getExecuteActiveList() {
        String sql = "select * from gflow_executeconfig where active=1";
        return jdbcUtils.queryForList(sql, null, GFlowExecuteConfig.class);
    }

    @Override
    public List<GFlowTrigger> getTriggerList(long triggerGroupId) {
        String sql = "select * from gflow_trigger where trigger_group_id=?";
        return jdbcUtils.queryForList(sql, new Object[]{triggerGroupId}, GFlowTrigger.class);
    }
}
