package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.sql.ConfigDao;
import com.gsralex.gflow.core.domain.Action;
import com.gsralex.gflow.core.domain.ExecuteConfig;
import com.gsralex.gflow.core.domain.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
@Repository
public class ConfigDaoImpl implements ConfigDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public Action getAction(long id) {
        String sql = "select * from gflow_action where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, Action.class);
    }

    @Override
    public List<ExecuteConfig> listExecuteConfig() {
        String sql = "select * from gflow_executeconfig";
        return jdbcUtils.queryForList(sql, null, ExecuteConfig.class);
    }

    @Override
    public List<Flow> getTriggerList(long triggerGroupId) {
        String sql = "select * from gflow_trigger where action_group_id=?";
        return jdbcUtils.queryForList(sql, new Object[]{triggerGroupId}, Flow.class);
    }

}
