package com.gsralex.gflow.web.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.domain.FlowGroupPo;
import com.gsralex.gflow.web.dao.FlowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Repository
public class FlowDaoImpl implements FlowDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public List<FlowGroupPo> listFlowGroup() {
        String sql = "select * from gflow_flowgroup where del=0";
        return jdbcUtils.queryForList(sql, null, FlowGroupPo.class);
    }
}
