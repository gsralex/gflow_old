package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.core.domain.FlowDirectPo;
import com.gsralex.gflow.core.domain.FlowGroupPo;
import com.gsralex.gflow.core.domain.FlowItemPo;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface FlowDao {

    boolean saveFlowGroup(FlowGroupPo flowGroupPo);

    boolean updateFlowGroup(FlowGroupPo flowGroupPo);

    FlowGroupPo getFlowGroup(long id);

    boolean removeFlowGroup(long id);

    int batchSaveFlowItem(List<FlowItemPo> list);

    int batchUpdateFlowItem(List<FlowItemPo> list);

    int batchRemoveFlowItem(List<FlowItemPo> list);

    List<FlowItemPo> listFlowItem(long groupId);

    int batchSaveFlowDirect(List<FlowDirectPo> list);

    int batchUpdateFlowDirect(List<FlowDirectPo> list);

    int batchRemoveFlowDirect(List<FlowDirectPo> list);

    List<FlowDirectPo> listFlowDirect(long groupId);
}
