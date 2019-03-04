package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.dao.ActionDao;
import com.gsralex.gflow.scheduler.domain.Action;

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
    public boolean saveAction(Action action) {
        return jdbcUtils.insert(action);
    }

    @Override
    public boolean updateAction(Action action) {
        return jdbcUtils.update(action);
    }

    @Override
    public boolean removeAction(long id) {
        String sql = "update gflow_action set del=1 where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{id}) != 0 ? true : false;
    }

    @Override
    public Action getAction(long id) {
        String sql = "select * from gflow_action where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, Action.class);
    }

    @Override
    public List<Action> listAction(int pageSize, int pageIndex) {
        int skip = (pageIndex - 1) * pageSize;
        String sql = "select * from gflow_action limit " + skip + "," + pageSize;
        return jdbcUtils.queryForList(sql, null, Action.class);
    }


}
