package com.gsralex.gflow.scheduler.service;

import com.gsralex.gflow.pub.domain.FlowDirect;
import com.gsralex.gflow.pub.domain.FlowGroup;
import com.gsralex.gflow.pub.domain.FlowItem;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/10
 */
public interface FlowService {

    boolean saveFlowGroup(FlowGroup flowGroup, List<FlowItem> itemList, List<FlowDirect> directList);

    boolean updateFlowGroup(FlowGroup flowGroup, List<FlowItem> itemList, List<FlowDirect> directList);

}
