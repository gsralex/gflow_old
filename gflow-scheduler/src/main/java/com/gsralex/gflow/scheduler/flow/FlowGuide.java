package com.gsralex.gflow.scheduler.flow;

import com.gsralex.gflow.core.domain.Job;
import com.gsralex.gflow.core.domain.Flow;
import com.gsralex.gflow.core.enums.JobGroupStatusEnum;
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
    private JobGroupStatusEnum status;

    public FlowGuide(long groupId, List<Flow> triggers, JobGroupStatusEnum status) {
        this(groupId, triggers, null, status);
    }

    /**
     * 初始化流程向导
     *
     * @param groupId  流程组id
     * @param triggers 触发器配置
     * @param jobs     所有流程组的执行的任务
     */
    public FlowGuide(long groupId, List<Flow> triggers, List<Job> jobs, JobGroupStatusEnum status) {
        this.groupId = groupId;
        this.posMap = new HashMap<>();
        this.actionMap = new HashMap<>();
        this.status = status;

        for (Flow trigger : triggers) {
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
            for (Job job : jobs) {
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

    public boolean isFinish() {
        for (Map.Entry<Integer, FlowNode> entry : posMap.entrySet()) {
            if (!entry.getValue().isOk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取还有可以继续执行的任务
     *
     * @return
     */
    public List<FlowNode> listContinueAction() {
        List<FlowNode> continueActionList = new ArrayList<>();
        for (Map.Entry<Integer, FlowNode> entry : posMap.entrySet()) {
            if (!entry.getValue().isOk()) {
                boolean finish = true;
                for (FlowNode pre : entry.getValue().getPre()) {
                    if (!pre.isOk()) {
                        finish = false;
                        break;
                    }
                }
                if (finish) {
                    continueActionList.add(entry.getValue());
                }
            }
        }
        return continueActionList;
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

    public JobGroupStatusEnum getStatus() {
        return status;
    }

    public void setStatus(JobGroupStatusEnum status) {
        this.status = status;
    }
}
