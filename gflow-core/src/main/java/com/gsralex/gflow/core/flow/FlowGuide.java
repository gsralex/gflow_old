package com.gsralex.gflow.core.flow;

import com.gsralex.gflow.core.domain.GFlowJob;
import com.gsralex.gflow.core.domain.GFlowTrigger;
import com.gsralex.gflow.core.enums.JobStatusEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/8/17
 */
public class FlowGuide {

    private static final Long ROOT_ACTIONID = 0L;

    private Map<Integer, FlowNode> posMap;
    private Map<Long, List<FlowNode>> actionMap;
    private long groupId;

    public FlowGuide(long groupId, List<GFlowTrigger> triggers) {
        this(groupId, triggers, null);
    }

    public FlowGuide(long groupId, List<GFlowTrigger> triggers, List<GFlowJob> jobs) {
        this.groupId = groupId;
        this.posMap = new HashMap<>();
        this.actionMap = new HashMap<>();

        for (GFlowTrigger trigger : triggers) {
            long actionId = trigger.getActionId();
            long preActionId = trigger.getPreActionId();
            int preIndex = trigger.getPreIndex();
            int index = trigger.getIndex();
            //处理前置节点
            FlowNode current = putPosNode(index, actionId);
            if (preActionId != ROOT_ACTIONID) {
                FlowNode pre = putPosNode(preIndex, preActionId);
                pre.getNext().add(current);
                current.getPre().add(pre);
            }
            putActionNode(preActionId, current);
        }

        if (jobs != null) {
            for (GFlowJob job : jobs) {
                this.updateNodeOk(job.getIndex(), job.getStatus() == JobStatusEnum.ExecuteOk.getValue());
            }
        }
    }


    public List<FlowNode> listRoot() {
        return actionMap.get(ROOT_ACTIONID);
    }

    public void updateNodeOk(int index, boolean ok) {
        FlowNode node = posMap.get(index);
        node.setOk(ok);
    }

    public List<FlowNode> listNeedAction(int position) {
        List<FlowNode> needActionList = new ArrayList<>();
        FlowNode node = posMap.get(position);
        //检查后置节点的前置节点是否都已完成
        for (FlowNode next : node.getNext()) {
            boolean finish = true;
            for (FlowNode pre : next.getPre()) {
                if (!pre.isOk()) {
                    finish = false;
                    break;
                }
            }
            if (finish) {
                synchronized (next) {
                    if (!next.isSchedule()) {
                        next.setSchedule(true);
                        needActionList.add(next);
                    }
                }
            }
        }
        return needActionList;
    }


    private FlowNode putPosNode(int index, long actionId) {
        FlowNode node;
        if (!posMap.containsKey(index)) {
            node = new FlowNode();
            node.setActionId(actionId);
            node.setIndex(index);
            posMap.put(index, node);
        } else {
            node = posMap.get(index);
        }
        return node;
    }

    private void putActionNode(long actionId, FlowNode node) {
        if (!actionMap.containsKey(actionId)) {
            actionMap.put(actionId, new ArrayList<>());
        }
        actionMap.get(actionId).add(node);
    }


}
