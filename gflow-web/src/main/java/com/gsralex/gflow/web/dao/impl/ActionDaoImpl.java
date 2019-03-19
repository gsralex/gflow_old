package com.gsralex.gflow.web.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.domain.ActionPo;
import com.gsralex.gflow.web.dao.ActionDao;
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
public class ActionDaoImpl implements ActionDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    public ActionDaoImpl(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public boolean saveAction(ActionPo actionPo) {
        return jdbcUtils.insert(actionPo);
    }

    @Override
    public boolean updateAction(ActionPo actionPo) {
        return jdbcUtils.update(actionPo);
    }

    @Override
    public boolean removeAction(long id) {
        String sql = "update gflow_action set del=1 where id=? and del=0";
        return jdbcUtils.executeUpdate(sql, new Object[]{id}) != 0 ? true : false;
    }

    @Override
    public ActionPo getAction(long id) {
        String sql = "select * from gflow_action where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, ActionPo.class);
    }

    @Override
    public List<ActionPo> listAction(String tag, String name, String className, int pageSize, int pageIndex) {
        String sql = "select * from gflow_action where del=0 ";
        List<Object> paramList = new ArrayList<>();
        if (!StringUtils.isEmpty(tag)) {
            sql += "and tag like ? ";
            paramList.add("%" + tag + "%");
        }
        if (!StringUtils.isEmpty(name)) {
            sql += "and name like ? ";
            paramList.add("%" + name + "%");
        }
        if (!StringUtils.isEmpty(className)) {
            sql += "and class_name like ? ";
            paramList.add("%" + className + "%");
        }
        return jdbcUtils.queryForList(sql, paramList.toArray(), ActionPo.class);
    }

    @Override
    public int countAction(String tag, String name, String className) {
        String sql = "select count(1) from gflow_action where 1=1 ";
        List<Object> paramList = new ArrayList<>();
        if (!StringUtils.isEmpty(tag)) {
            sql += "and tag like ? ";
            paramList.add("%" + tag + "%");
        }
        if (!StringUtils.isEmpty(name)) {
            sql += "and name like ? ";
            paramList.add("%" + name + "%");
        }
        if (!StringUtils.isEmpty(className)) {
            sql += "and class_name like ? ";
            paramList.add("%" + className + "%");
        }
        return jdbcUtils.queryForObject(sql, paramList.toArray(), Integer.class);
    }
}
