package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.core.domain.FlowDirectPo;
import com.gsralex.gflow.core.domain.FlowGroupPo;
import com.gsralex.gflow.core.domain.FlowItemPo;
import com.gsralex.gflow.scheduler.dao.FlowDao;
import com.gsralex.gflow.scheduler.service.BizFlowService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/3/10
 */
@Service
public class BizFlowServiceImpl implements BizFlowService {

    @Autowired
    private FlowDao flowDao;

    @Override
    public boolean saveFlowGroup(FlowGroupPo flowGroupPo, List<FlowItemPo> itemList, List<FlowDirectPo> directList) {
        flowGroupPo.setCreateTime(System.currentTimeMillis());
        flowGroupPo.setDel(false);
        flowDao.saveFlowGroup(flowGroupPo);
        for (FlowItemPo item : itemList) {
            item.setCreateTime(System.currentTimeMillis());
            item.setActionGroupId(flowGroupPo.getId());
        }
        flowDao.batchSaveFlowItem(itemList);
        for (FlowDirectPo direct : directList) {
            direct.setGroupId(flowGroupPo.getId());
        }
        flowDao.batchSaveFlowDirect(directList);
        return true;
    }

    @Override
    public boolean updateFlowGroup(FlowGroupPo flowGroupPo, List<FlowItemPo> itemList, List<FlowDirectPo> directList) {
        FlowGroupPo dataFlowGroupPo = flowDao.getFlowGroup(flowGroupPo.getId());
        if (dataFlowGroupPo != null) {
            dataFlowGroupPo.setName(flowGroupPo.getName());
            dataFlowGroupPo.setDescription(flowGroupPo.getDescription());
            dataFlowGroupPo.setModTime(System.currentTimeMillis());
            flowDao.updateFlowGroup(dataFlowGroupPo);
            updateFlowItem(flowGroupPo.getId(), itemList);
            updateFlowDirect(flowGroupPo.getId(), directList);
            return true;
        }
        return false;
    }

    private boolean updateFlowItem(long groupId, List<FlowItemPo> itemList) {
        List<FlowItemPo> dataItemList = flowDao.listFlowItem(groupId);
        //add
        List<FlowItemPo> saveList = new ArrayList<>();
        List<FlowItemPo> updateList = new ArrayList<>();
        List<FlowItemPo> removeList = new ArrayList<>();
        Map<Integer, FlowItemPo> dataIndexMap = new HashMap<>();
        for (FlowItemPo item : dataItemList) {
            dataIndexMap.put(item.getIndex(), item);
        }
        for (FlowItemPo flowItemPo : itemList) {
            if (dataIndexMap.containsKey(flowItemPo.getIndex())) {
                FlowItemPo dataItem = dataIndexMap.get(flowItemPo.getIndex());
                boolean changed = !(flowItemPo.getActionId() == dataItem.getActionId()
                        && StringUtils.equals(flowItemPo.getParameter(), dataItem.getParameter())
                        && StringUtils.equals(flowItemPo.getLabel(), dataItem.getLabel()));
                if (changed) {
                    dataItem.setActionId(flowItemPo.getActionId());
                    dataItem.setParameter(flowItemPo.getParameter());
                    dataItem.setLabel(flowItemPo.getLabel());
                    updateList.add(dataItem);
                }
            } else {
                flowItemPo.setCreateTime(System.currentTimeMillis());
                flowItemPo.setActionGroupId(groupId);
                saveList.add(flowItemPo);
            }
        }
        Map<Integer, FlowItemPo> indexMap = new HashMap<>();
        for (FlowItemPo item : itemList) {
            indexMap.put(item.getIndex(), item);
        }
        //remove
        for (FlowItemPo dataFlowItemPo : dataItemList) {
            if (!indexMap.containsKey(dataFlowItemPo.getIndex())) {
                removeList.add(dataFlowItemPo);
            }
        }
        flowDao.batchSaveFlowItem(saveList);
        flowDao.batchUpdateFlowItem(updateList);
        flowDao.batchRemoveFlowItem(removeList);
        return true;
    }

    private boolean updateFlowDirect(long groupId, List<FlowDirectPo> directList) {
        List<FlowDirectPo> dataDirectList = flowDao.listFlowDirect(groupId);

        Map<Integer, FlowDirectPo> dataDirectMap = new HashMap<>();
        for (FlowDirectPo direct : dataDirectList) {
            dataDirectMap.put(direct.getIndex(), direct);
        }
        List<FlowDirectPo> saveList = new ArrayList<>();
        List<FlowDirectPo> updateList = new ArrayList<>();
        for (FlowDirectPo direct : directList) {
            if (dataDirectMap.containsKey(direct.getIndex())) {
                FlowDirectPo dataDirect = dataDirectMap.get(direct.getIndex());
                if (!dataDirect.getNextIndex().equals(direct.getNextIndex())) {
                    dataDirect.setNextIndex(direct.getNextIndex());
                    updateList.add(dataDirect);
                }
            } else {
                direct.setGroupId(groupId);
                saveList.add(direct);
            }
        }

        Map<Integer, FlowDirectPo> directMap = new HashMap<>();
        List<FlowDirectPo> removeList = new ArrayList<>();
        for (FlowDirectPo direct : directList) {
            directMap.put(direct.getIndex(), direct);
        }
        for (FlowDirectPo direct : dataDirectList) {
            if (!directMap.containsKey(direct.getIndex())) {
                removeList.add(direct);
            }
        }
        flowDao.batchSaveFlowDirect(saveList);
        flowDao.batchUpdateFlowDirect(updateList);
        flowDao.batchRemoveFlowDirect(removeList);
        return true;
    }


    public boolean removeFlowGroup(long id) {
        FlowGroupPo flowGroupPo = flowDao.getFlowGroup(id);
        if (flowGroupPo != null) {
            return flowDao.removeFlowGroup(id);
        }
        return false;
    }
}
