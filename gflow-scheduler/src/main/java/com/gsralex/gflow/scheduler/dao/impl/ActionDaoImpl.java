package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.domain.ActionPo;
import com.gsralex.gflow.scheduler.dao.ActionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Repository
public class ActionDaoImpl implements ActionDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public ActionPo getAction(long id) {
        String sql = "select * from gflow_action where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, ActionPo.class);
    }
}
