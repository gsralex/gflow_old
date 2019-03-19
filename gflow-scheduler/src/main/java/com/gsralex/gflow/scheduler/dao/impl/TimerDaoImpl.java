package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.domain.TimerConfigPo;
import com.gsralex.gflow.scheduler.dao.TimerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/12/18
 */
@Repository
public class TimerDaoImpl implements TimerDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public boolean saveTimer(TimerConfigPo config) {
        return jdbcUtils.insert(config,true);
    }

    @Override
    public boolean updateTimer(TimerConfigPo config) {
        return jdbcUtils.update(config);
    }

    @Override
    public boolean deleteTimer(long id) {
        String sql = "update gflow_timerconfig set del=1 where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{id}) != 0 ? true : false;
    }

    @Override
    public TimerConfigPo getTimer(long id) {
        String sql="select * from gflow_timerconfig where id=? and del =0 ";
        return jdbcUtils.queryForObject(sql,new Object[]{id}, TimerConfigPo.class);
    }

    @Override
    public List<TimerConfigPo> listTimer() {
        String sql = "select * from gflow_timerconfig where del=0";
        return jdbcUtils.queryForList(sql, null, TimerConfigPo.class);
    }

}
