package com.gsralex.gflow.scheduler.flow;

import com.gsralex.gflow.core.domain.FlowDirectPo;
import com.gsralex.gflow.core.domain.FlowItemPo;
import com.gsralex.gflow.core.domain.JobPo;
import com.gsralex.gflow.core.enums.JobGroupStatus;
import com.gsralex.gflow.core.enums.JobStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/8/17
 */
public class FlowGuide {

    private static final int ROOT_INDEX = 0;

    //indexMap key->index,value->flownode
    private Map<Integer, FlowNode> posMap = new HashMap<>();
    private long groupId;
    private JobGroupStatus status;

    public FlowGuide(long groupId, List<FlowItemPo> flowItemPos, List<FlowDirectPo> directs,
                     JobGroupStatus status) {
        this(groupId, flowItemPos, directs, null, status);
    }

    /**
     * 初始化流程向导
     *
     * @param groupId   流程组id
     * @param flowItemPos 流程配置
     * @param directs   流程流转配置
     * @param jobPos      所有流程组的执行的任务
     */
    public FlowGuide(long groupId, List<FlowItemPo> flowItemPos, List<FlowDirectPo> directs,
                     List<JobPo> jobPos, JobGroupStatus status) {
        this.groupId = groupId;
        this.status = status;

//        Map<Integer, List<Integer>> directMap = new HashMap<>();
//        for (FlowDirectPo item : directs) {
//            List<Integer> preList = new ArrayList<>();
//            String[] indexStrArr = item.getNextIndex().split(",");
//            for (String indexStr : indexStrArr) {
//                preList.add(Integer.parseInt(indexStr));
//            }
//            directMap.put(item.getIndex(), preList);
//        }

        for (FlowItemPo trigger : flowItemPos) {
            FlowNode current = new FlowNode();
            current.setActionId(trigger.getActionId());
            current.setIndex(trigger.getIndex());
            current.setParameter(trigger.getParameter());
            posMap.put(trigger.getIndex(), current);
        }
        for (FlowDirectPo direct : directs) {
            FlowNode node = posMap.get(direct.getIndex());
            //0 1
            //1 2
            if (direct.getIndex() != ROOT_INDEX) {
                String[] indexStrArr = direct.getNextIndex().split(",");
                for (String indexStr : indexStrArr) {
                    int index = Integer.parseInt(indexStr);
                    FlowNode nextNode = posMap.get(index);
                    nextNode.getPre().add(node);
                    node.getNext().add(nextNode);
                }
            }
        }

        if (jobPos != null) {
            for (JobPo jobPo : jobPos) {
                this.updateNodeOk(jobPo.getIndex(), jobPo.getStatus() == JobStatus.ExecuteOk.getValue());
            }
        }
    }

    /**
     * 列出根节点下待执行的节点
     *
     * @return
     */
    public List<FlowNode> listRoot() {
        List<FlowNode> rootList = new ArrayList<>();
        for (Map.Entry<Integer, FlowNode> entry : posMap.entrySet()) {
            FlowNode node = entry.getValue();
            if (node.getPre().size() == 0) {
                rootList.add(node);
            }
        }
        return rootList;
    }

    public void updateNodeOk(int index, boolean ok) {
        FlowNode node = posMap.get(index);
        node.setOk(ok);
    }

    public List<FlowNode> listNeedAction(int index) {
        List<FlowNode> needActionList = new ArrayList<>();
        FlowNode node = posMap.get(index);
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
                needActionList.add(next);
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
        List<FlowNode> continueList = new ArrayList<>();
        for (Map.Entry<Integer, FlowNode> entry : posMap.entrySet()) {
            if (!entry.getValue().isOk()) {
                boolean finish = true; //默认已完成，当没有前置节点时
                for (FlowNode pre : entry.getValue().getPre()) {
                    if (!pre.isOk()) {
                        finish = false;
                        break;
                    }
                }
                if (finish) {
                    continueList.add(entry.getValue());
                }
            }
        }
        return continueList;
    }


    public JobGroupStatus getStatus() {
        return status;
    }

    public void setStatus(JobGroupStatus status) {
        this.status = status;
    }
}
