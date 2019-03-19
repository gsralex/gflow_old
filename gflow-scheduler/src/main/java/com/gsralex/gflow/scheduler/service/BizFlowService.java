package com.gsralex.gflow.scheduler.service;

import com.gsralex.gflow.core.domain.FlowDirectPo;
import com.gsralex.gflow.core.domain.FlowGroupPo;
import com.gsralex.gflow.core.domain.FlowItemPo;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/10
 */
public interface BizFlowService {

    boolean saveFlowGroup(FlowGroupPo flowGroupPo, List<FlowItemPo> itemList, List<FlowDirectPo> directList);

    boolean updateFlowGroup(FlowGroupPo flowGroupPo, List<FlowItemPo> itemList, List<FlowDirectPo> directList);

}
