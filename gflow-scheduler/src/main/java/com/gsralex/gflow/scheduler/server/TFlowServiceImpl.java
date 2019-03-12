package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.pub.constants.ErrConstants;
import com.gsralex.gflow.pub.domain.FlowDirect;
import com.gsralex.gflow.pub.domain.FlowGroup;
import com.gsralex.gflow.pub.domain.FlowItem;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.flow.TFlowDirect;
import com.gsralex.gflow.pub.thriftgen.flow.TFlowGroup;
import com.gsralex.gflow.pub.thriftgen.flow.TFlowItem;
import com.gsralex.gflow.pub.thriftgen.flow.TFlowService;
import com.gsralex.gflow.scheduler.service.FlowService;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/10
 */
@Component
public class TFlowServiceImpl implements TFlowService.Iface {

    @Autowired
    private FlowService flowService;

    @Override
    public TResp setFlowGroup(TFlowGroup tGroup) throws TException {
        FlowGroup group = new FlowGroup();
        group.setId(tGroup.getId());
        group.setName(tGroup.getName());
        group.setDescription(tGroup.getDescription());

        List<FlowItem> itemList = new ArrayList<>();
        List<TFlowItem> tItemList = tGroup.getItemList();
        for (TFlowItem tItem : tItemList) {
            FlowItem item = new FlowItem();
            item.setActionId(tItem.getActionId());
            item.setIndex(tItem.getIndex());
            item.setParameter(tItem.getParameter());
            item.setLabel(tItem.getLabel());
            itemList.add(item);
        }
        List<FlowDirect> directList = new ArrayList<>();
        List<TFlowDirect> tDirectList = tGroup.getDirectList();
        for (TFlowDirect tDirect : tDirectList) {
            FlowDirect direct = new FlowDirect();
            direct.setIndex(tDirect.getIndex());
            direct.setNextIndex(StringUtils.join(tDirect.getNextIndex(), ","));
            directList.add(direct);
        }
        boolean ok;
        if (tGroup.getId() != 0) {
            ok = flowService.updateFlowGroup(group, itemList, directList);
        } else {
            ok = flowService.saveFlowGroup(group, itemList, directList);
        }
        TResp tResp = new TResp();
        tResp.setCode(ok ? ErrConstants.OK : ErrConstants.ERR_INTERNAL);
        return tResp;
    }
}
