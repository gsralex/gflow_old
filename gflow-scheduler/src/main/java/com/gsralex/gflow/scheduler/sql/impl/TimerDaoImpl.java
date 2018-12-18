package com.gsralex.gflow.scheduler.sql.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.scheduler.domain.TimerConfig;
import com.gsralex.gflow.scheduler.sql.TimerDao;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/12/18
 */
public class TimerDaoImpl implements TimerDao {


    private JdbcUtils jdbcUtils;

    public TimerDaoImpl(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public boolean saveTimer(TimerConfig config) {
        return jdbcUtils.insert(config);
    }

    @Override
    public boolean updateTimer(TimerConfig config) {
        return jdbcUtils.update(config);
    }

    @Override
    public boolean deleteTimer(long id) {
        String sql = "update gflow_timer set del=1 where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{id}) != 0 ? true : false;
    }

    @Override
    public List<TimerConfig> listTimer() {
        String sql = "select * from gflow_timer where del=0";
        return jdbcUtils.queryForList(sql, null, TimerConfig.class);
    }

}
