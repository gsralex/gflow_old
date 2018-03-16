package com.gsralex.gflow.core.dao.impl;

import com.gsralex.gdata.jdbc.JdbcUtils;
import com.gsralex.gflow.core.dao.JobDao;
import com.gsralex.gflow.core.domain.GFlowJobGroup;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public class JobDaoImpl implements JobDao {


    private JdbcUtils jdbcUtils;

    @Override
    public boolean saveJobGroup(GFlowJobGroup jobGroup) {
        return jdbcUtils.insert(jobGroup, true);
    }
}
