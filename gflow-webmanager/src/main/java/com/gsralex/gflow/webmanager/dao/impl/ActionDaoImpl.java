package com.gsralex.gflow.webmanager.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.domain.GFlowAction;
import com.gsralex.gflow.webmanager.model.PageDto;
import com.gsralex.gflow.webmanager.dao.ActionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/9/5
 */
@Repository
public class ActionDaoImpl implements ActionDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public boolean saveAction(GFlowAction action) {
        return jdbcUtils.insert(action);
    }

    @Override
    public boolean existsActionClass(long id, String className) {
        String sql = "select count(1) from gflow_action where class_name=? and id<>?";
        int cnt = jdbcUtils.queryForObject(sql, new Object[]{className, id}, Integer.class);
        return cnt != 0 ? true : false;
    }

    @Override
    public PageDto listAction(int pageSize, int pageIndex) {
        int skip = (pageIndex - 1) * pageSize;
        int limit = pageSize;
        String sql = "select * from gflow_action limit " + skip + "," + limit;
        String countSql = "select count(1) from gflow_action";
        List<GFlowAction> list = jdbcUtils.queryForList(sql, null, GFlowAction.class);
        int cnt = jdbcUtils.queryForObject(countSql, null, Integer.class);
        PageDto dto = new PageDto();
        dto.setData(list);
        dto.setCnt(cnt);
        dto.setPageSize(pageSize);
        dto.setPageIndex(pageIndex);
        return dto;
    }
}
