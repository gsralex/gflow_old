package com.gsralex.gflow.web.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.pub.domain.TimerConfig;
import com.gsralex.gflow.web.dao.TimerDao;
import com.gsralex.gflow.web.model.TimerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Repository
public class TimerDaoImpl implements TimerDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public TimerConfig getTimer(long id) {
        String sql = "select * from gflow_timerconfig where id=? and del=0 ";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, TimerConfig.class);
    }

    @Override
    public List<TimerVo> listTimer(int pageSize, int pageIndex) {
        String sql = "select timer.*,flowgroup.name from gflow_timerconfig as timer left join gflow_flowgroup as flowgroup " +
                "on timer.flow_group_id=flowgroup.id where timer.del=0 order by id desc ";
        int skip = (pageIndex - 1) * pageSize;
        sql += "limit " + skip + "," + pageSize;
        return jdbcUtils.queryForList(sql, null, TimerVo.class);

    }

    @Override
    public int countTimer() {
        String sql = "select count(1) from gflow_timerconfig where del=0 ";
        return jdbcUtils.queryForObject(sql, null, Integer.class);
    }
}
