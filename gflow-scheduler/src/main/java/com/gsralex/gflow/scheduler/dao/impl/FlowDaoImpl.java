package com.gsralex.gflow.scheduler.dao.impl;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.domain.FlowDirectPo;
import com.gsralex.gflow.core.domain.FlowGroupPo;
import com.gsralex.gflow.core.domain.FlowItemPo;
import com.gsralex.gflow.scheduler.dao.FlowDao;
import com.gsralex.gflow.scheduler.dao.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Repository
public class FlowDaoImpl implements FlowDao {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public boolean saveFlowGroup(FlowGroupPo flowGroupPo) {
        return jdbcUtils.insert(flowGroupPo, true);
    }

    @Override
    public boolean updateFlowGroup(FlowGroupPo flowGroupPo) {
        return jdbcUtils.update(flowGroupPo);
    }

    @Override
    public FlowGroupPo getFlowGroup(long id) {
        String sql = "select * from gflow_flowgroup where id=?";
        return jdbcUtils.queryForObject(sql, new Object[]{id}, FlowGroupPo.class);
    }

    @Override
    public boolean removeFlowGroup(long id) {
        String sql = "update gflow_flowgroup set del=1 where id=?";
        return jdbcUtils.executeUpdate(sql, new Object[]{id}) != 0 ? true : false;
    }

    @Override
    public int batchSaveFlowItem(List<FlowItemPo> list) {
        return jdbcUtils.batchInsert(list, true);
    }

    @Override
    public int batchUpdateFlowItem(List<FlowItemPo> list) {
        return jdbcUtils.batchUpdate(list);
    }

    @Override
    public int batchRemoveFlowItem(List<FlowItemPo> list) {
        if (list != null && list.size() != 0) {
            List<Long> idList = list.stream().map(x -> x.getId()).collect(Collectors.toList());
            String ids = IdUtils.longToInts(idList);
            String sql = "delete from gflow_flowitem where id in (" + ids + ")";
            return jdbcUtils.executeUpdate(sql, null);
        }
        return 0;
    }

    @Override
    public List<FlowItemPo> listFlowItem(long groupId) {
        String sql = "select * from gflow_flowitem where action_group_id=?";
        return jdbcUtils.queryForList(sql, new Object[]{groupId}, FlowItemPo.class);
    }

    @Override
    public int batchSaveFlowDirect(List<FlowDirectPo> list) {
        return jdbcUtils.batchInsert(list, true);
    }

    @Override
    public int batchUpdateFlowDirect(List<FlowDirectPo> list) {
        return jdbcUtils.batchUpdate(list);
    }

    @Override
    public int batchRemoveFlowDirect(List<FlowDirectPo> list) {
        if (list != null && list.size() != 0) {
            List<Long> idList = list.stream().map(x -> x.getId()).collect(Collectors.toList());
            String ids = IdUtils.longToInts(idList);
            String sql = "delete from gflow_flowdirect where id in (" + ids + ")";
            return jdbcUtils.executeUpdate(sql, null);
        }
        return 0;
    }

    @Override
    public List<FlowDirectPo> listFlowDirect(long groupId) {
        String sql = "select * from gflow_flowdirect where group_id=?";
        return jdbcUtils.queryForList(sql, new Object[]{groupId}, FlowDirectPo.class);
    }
}
