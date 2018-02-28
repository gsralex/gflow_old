package com.gsralex.gflow.core.dao.impl;

import com.gsralex.gflow.core.constant.DbTableConstants;
import com.gsralex.gflow.core.dao.ExecutionDao;
import com.gsralex.gflow.core.dao.TableUtils;
import com.gsralex.gflow.core.dao.helper.JdbcUtils;
import com.gsralex.gflow.core.domain.GFlowExecution;

import java.util.List;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public class ExecutionDaoImpl implements ExecutionDao {

    private JdbcUtils jdbcUtils;

    @Override
    public List<GFlowExecution> listActiveExecution() {
        String sql = String.format("select * from %s where active=1",
                TableUtils.getTableName(DbTableConstants.GFLOW_EXECUTION));
        return jdbcUtils.query(sql,null,GFlowExecution.class);
    }
}
