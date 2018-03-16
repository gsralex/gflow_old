package com.gsralex.gflow.core.dao.impl;

import com.gsralex.gdata.jdbc.JdbcUtils;
import com.gsralex.gflow.core.dao.TriggerDao;
import com.gsralex.gflow.core.domain.GFlowJobGroup;

import java.util.List;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public class TriggerDaoImpl implements TriggerDao {

    private JdbcUtils jdbcUtils;

    @Override
    public List<GFlowJobGroup> listJobGroup(List<Long> groupIdList, int ds) {
        String ids = "";//IdUtils.longToInt(groupIdList);
        String sql = "select * from %s where trigger_group_id in (" + ids + ")  and ds=?";
        return jdbcUtils.query(sql, new Object[]{ds}, GFlowJobGroup.class);
    }
}
