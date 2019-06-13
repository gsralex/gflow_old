package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.domain.FlowDirectPo;
import com.gsralex.gflow.core.domain.FlowGroupPo;
import com.gsralex.gflow.core.domain.FlowItemPo;
import com.gsralex.gflow.scheduler.client.FlowService;
import com.gsralex.gflow.scheduler.client.domain.FlowDirect;
import com.gsralex.gflow.scheduler.client.domain.FlowGroup;
import com.gsralex.gflow.scheduler.client.domain.FlowItem;
import com.gsralex.gflow.scheduler.service.BizFlowService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/10
 */
@Component
public class FlowServiceImpl implements FlowService {

    @Autowired
    private BizFlowService bizFlowService;

    @Override
    public boolean setFlowGroup(FlowGroup flowGroup) {
        FlowGroupPo groupPo = new FlowGroupPo();
        groupPo.setId(flowGroup.getId());
        groupPo.setName(flowGroup.getName());
        groupPo.setDescription(flowGroup.getDescription());

        List<FlowItemPo> itemPoList = new ArrayList<>();
        List<FlowItem> itemList = flowGroup.getItemList();
        for (FlowItem item : itemList) {
            FlowItemPo itemPo = new FlowItemPo();
            itemPo.setActionId(item.getActionId());
            itemPo.setIndex(item.getIndex());
            itemPo.setParameter(item.getParameter());
            itemPo.setLabel(item.getLabel());
            itemPoList.add(itemPo);
        }
        List<FlowDirectPo> directPoList = new ArrayList<>();
        List<FlowDirect> directList = flowGroup.getDirectList();
        for (FlowDirect direct : directList) {
            FlowDirectPo directPo = new FlowDirectPo();
            directPo.setIndex(direct.getIndex());
            directPo.setNextIndex(StringUtils.join(direct.getNextIndex(), ","));
            directPoList.add(directPo);
        }
        if (flowGroup.getId() != 0) {
            return bizFlowService.updateFlowGroup(groupPo, itemPoList, directPoList);
        } else {
            return bizFlowService.saveFlowGroup(groupPo, itemPoList, directPoList);
        }
    }
}
