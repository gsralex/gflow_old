package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.domain.*;
import com.gsralex.gflow.scheduler.sql.ActionDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public class ActionDaoImpl implements ActionDao {

    private JdbcUtils jdbcUtils;

    public ActionDaoImpl(JdbcUtils jdbcUtils) {
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

}
