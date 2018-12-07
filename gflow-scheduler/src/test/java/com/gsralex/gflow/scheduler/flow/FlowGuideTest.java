package com.gsralex.gflow.scheduler.flow;

import com.gsralex.gflow.core.domain.Flow;
import com.gsralex.gflow.core.domain.FlowDirect;
import com.gsralex.gflow.core.enums.JobGroupStatusEnum;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author gsralex
 * @version 2018/12/7
 */
public class FlowGuideTest {
    @Test
    public void listRoot() throws Exception {
        long groupId = 0;
        List<Flow> flowList = new ArrayList<>();
        Flow flow1 = new Flow();
        flow1.setIndex(1);
        flow1.setActionId(1);
        flowList.add(flow1);
        Flow flow2 = new Flow();
        flow2.setIndex(2);
        flow2.setActionId(2);
        flowList.add(flow2);

        List<FlowDirect> directList = new ArrayList<>();
        FlowDirect direct1 = new FlowDirect();
        direct1.setIndex(1);
        direct1.setPreIndex(0);
        directList.add(direct1);
        FlowDirect direct2 = new FlowDirect();
        direct2.setIndex(2);
        direct2.setPreIndex(1);
        directList.add(direct2);
        FlowGuide flowGuide = new FlowGuide(groupId, flowList, directList, JobGroupStatusEnum.EXECUTING);
        Assert.assertEquals(1, flowGuide.listRoot().size());
        Assert.assertEquals(flow1.getIndex(), flowGuide.listRoot().get(0).getIndex());
    }

    @Test
    public void listNeedAction() throws Exception {
        long groupId = 0;
        List<Flow> flowList = new ArrayList<>();
        Flow flow1 = new Flow();
        flow1.setIndex(1);
        flow1.setActionId(1);
        flowList.add(flow1);
        Flow flow2 = new Flow();
        flow2.setIndex(2);
        flow2.setActionId(2);
        flowList.add(flow2);
        Flow flow3 = new Flow();
        flow3.setIndex(3);
        flow3.setActionId(3);
        flowList.add(flow3);

        List<FlowDirect> directList = new ArrayList<>();
        FlowDirect direct1 = new FlowDirect();
        direct1.setIndex(1);
        direct1.setPreIndex(0);
        directList.add(direct1);
        FlowDirect direct2 = new FlowDirect();
        direct2.setIndex(2);
        direct2.setPreIndex(1);
        directList.add(direct2);
        FlowDirect direct3 = new FlowDirect();
        direct3.setIndex(3);
        direct3.setPreIndex(1);
        directList.add(direct3);

        FlowGuide flowGuide = new FlowGuide(groupId, flowList, directList, JobGroupStatusEnum.EXECUTING);
        flowGuide.updateNodeOk(1, true);
        Assert.assertEquals(2, flowGuide.listNeedAction(1).size());
        Assert.assertEquals(2, flowGuide.listNeedAction(1).get(0).getIndex());
        Assert.assertEquals(3, flowGuide.listNeedAction(1).get(1).getIndex());
    }

    @Test
    public void listContinueAction() throws Exception {
        long groupId = 0;
        List<Flow> flowList = new ArrayList<>();
        Flow flow1 = new Flow();
        flow1.setIndex(1);
        flow1.setActionId(1);
        flowList.add(flow1);
        Flow flow2 = new Flow();
        flow2.setIndex(2);
        flow2.setActionId(2);
        flowList.add(flow2);
        Flow flow3 = new Flow();
        flow3.setIndex(3);
        flow3.setActionId(3);
        flowList.add(flow3);

        List<FlowDirect> directList = new ArrayList<>();
        FlowDirect direct1 = new FlowDirect();
        direct1.setPreIndex(0);
        direct1.setIndex(1);
        directList.add(direct1);
        FlowDirect direct2 = new FlowDirect();
        direct2.setPreIndex(1);
        direct2.setIndex(2);
        directList.add(direct2);
        FlowDirect direct3 = new FlowDirect();
        direct3.setPreIndex(1);
        direct3.setIndex(3);
        directList.add(direct3);

        FlowGuide flowGuide = new FlowGuide(groupId, flowList, directList, JobGroupStatusEnum.EXECUTING);
        Assert.assertEquals(1, flowGuide.listContinueAction().size());
        flowGuide.updateNodeOk(1, true);
        Assert.assertEquals(2, flowGuide.listContinueAction().size());
        flowGuide.updateNodeOk(2, true);
        Assert.assertEquals(1, flowGuide.listContinueAction().size());
        flowGuide.updateNodeOk(3, true);
        Assert.assertEquals(0, flowGuide.listContinueAction().size());
    }
}