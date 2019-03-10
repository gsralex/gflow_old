package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.pub.domain.FlowDirect;
import com.gsralex.gflow.pub.domain.FlowGroup;
import com.gsralex.gflow.pub.domain.FlowItem;
import com.gsralex.gflow.scheduler.dao.FlowDao;
import com.gsralex.gflow.scheduler.service.FlowService;
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
public class FlowServiceImpl implements FlowService {

    @Autowired
    private FlowDao flowDao;

    @Override
    public boolean saveFlowGroup(FlowGroup flowGroup, List<FlowItem> itemList, List<FlowDirect> directList) {
        flowGroup.setCreateTime(System.currentTimeMillis());
        flowGroup.setDel(false);
        flowDao.saveFlowGroup(flowGroup);
        for (FlowItem item : itemList) {
            item.setCreateTime(System.currentTimeMillis());
            item.setActionGroupId(flowGroup.getId());
        }
        flowDao.batchSaveFlowItem(itemList);
        for (FlowDirect direct : directList) {
            direct.setGroupId(flowGroup.getId());
        }
        flowDao.batchSaveFlowDirect(directList);
        return true;
    }

    @Override
    public boolean updateFlowGroup(FlowGroup flowGroup, List<FlowItem> itemList, List<FlowDirect> directList) {
        FlowGroup dataFlowGroup = flowDao.getFlowGroup(flowGroup.getId());
        if (dataFlowGroup != null) {
            dataFlowGroup.setName(flowGroup.getName());
            dataFlowGroup.setDescription(flowGroup.getDescription());
            dataFlowGroup.setModTime(System.currentTimeMillis());
            flowDao.updateFlowGroup(dataFlowGroup);
            updateFlowItem(flowGroup.getId(), itemList);
            updateFlowDirect(flowGroup.getId(), directList);
            return true;
        }
        return false;
    }

    private int updateFlowItem(long groupId, List<FlowItem> itemList) {
        List<FlowItem> dataItemList = flowDao.listFlowItem(groupId);
        //添加
        List<FlowItem> saveList = new ArrayList<>();
        List<FlowItem> updateList = new ArrayList<>();
        List<FlowItem> removeList = new ArrayList<>();
        Map<Integer, FlowItem> dataIndexMap = new HashMap<>();
        for (FlowItem item : dataItemList) {
            dataIndexMap.put(item.getIndex(), item);
        }
        for (FlowItem flowItem : itemList) {
            if (dataIndexMap.containsKey(flowItem.getIndex())) {
                FlowItem dataItem = dataIndexMap.get(flowItem.getIndex());
                boolean changed = !(flowItem.getActionId() == dataItem.getActionId()
                        && StringUtils.equals(flowItem.getParameter(), dataItem.getParameter())
                        && StringUtils.equals(flowItem.getLabel(), dataItem.getLabel()));
                if (changed) {
                    dataItem.setActionId(flowItem.getActionId());
                    dataItem.setParameter(flowItem.getParameter());
                    updateList.add(dataItem);
                }
            } else {
                flowItem.setCreateTime(System.currentTimeMillis());
                flowItem.setActionGroupId(groupId);
                saveList.add(flowItem);
            }
        }
        Map<Integer, FlowItem> indexMap = new HashMap<>();
        for (FlowItem item : itemList) {
            indexMap.put(item.getIndex(), item);
        }
        //删除
        for (FlowItem dataFlowItem : dataItemList) {
            if (!indexMap.containsKey(dataFlowItem.getIndex())) {
                removeList.add(dataFlowItem);
            }
        }
        int cnt = 0;
        cnt += flowDao.batchSaveFlowItem(saveList);
        cnt += flowDao.batchUpdateFlowItem(updateList);
        cnt += flowDao.batchRemoveFlowItem(removeList);
        return cnt;
    }

    private int updateFlowDirect(long groupId, List<FlowDirect> directList) {
        List<FlowDirect> dataDirectList = flowDao.listFlowDirect(groupId);

        Map<Integer, FlowDirect> dataDirectMap = new HashMap<>();
        for (FlowDirect direct : dataDirectList) {
            dataDirectMap.put(direct.getIndex(), direct);
        }
        List<FlowDirect> saveList = new ArrayList<>();
        List<FlowDirect> updateList = new ArrayList<>();
        for (FlowDirect direct : directList) {
            if (dataDirectMap.containsKey(direct.getIndex())) {
                FlowDirect dataDirect = dataDirectMap.get(direct.getIndex());
                if (!dataDirect.getNextIndex().equals(direct.getNextIndex())) {
                    dataDirect.setNextIndex(direct.getNextIndex());
                    updateList.add(dataDirect);
                }
            } else {
                direct.setGroupId(groupId);
                saveList.add(direct);
            }
        }

        Map<Integer, FlowDirect> directMap = new HashMap<>();
        List<FlowDirect> removeList = new ArrayList<>();
        for (FlowDirect direct : directList) {
            directMap.put(direct.getIndex(), direct);
        }
        for (FlowDirect direct : dataDirectList) {
            if (!directMap.containsKey(direct.getIndex())) {
                removeList.add(direct);
            }
        }
        int cnt = 0;
        cnt += flowDao.batchSaveFlowDirect(saveList);
        cnt += flowDao.batchUpdateFlowDirect(updateList);
        cnt += flowDao.batchRemoveFlowDirect(removeList);
        return cnt;
    }


    public boolean removeFlowGroup(long id) {
        FlowGroup flowGroup = flowDao.getFlowGroup(id);
        if (flowGroup != null) {
            return flowDao.removeFlowGroup(id);
        }
        return false;
    }
}
