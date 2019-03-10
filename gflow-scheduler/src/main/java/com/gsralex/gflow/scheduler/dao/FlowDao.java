package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.pub.domain.FlowDirect;
import com.gsralex.gflow.pub.domain.FlowGroup;
import com.gsralex.gflow.pub.domain.FlowItem;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface FlowDao {

    boolean saveFlowGroup(FlowGroup flowGroup);

    boolean updateFlowGroup(FlowGroup flowGroup);

    FlowGroup getFlowGroup(long id);

    boolean removeFlowGroup(long id);

    int batchSaveFlowItem(List<FlowItem> list);

    int batchUpdateFlowItem(List<FlowItem> list);

    int batchRemoveFlowItem(List<FlowItem> list);

    List<FlowItem> listFlowItem(long groupId);

    int batchSaveFlowDirect(List<FlowDirect> list);

    int batchUpdateFlowDirect(List<FlowDirect> list);

    int batchRemoveFlowDirect(List<FlowDirect> list);

    List<FlowDirect> listFlowDirect(long groupId);
}
