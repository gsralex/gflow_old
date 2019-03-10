package com.gsralex.gflow.scheduler.flow;
import com.gsralex.gflow.pub.domain.FlowItem;
import com.gsralex.gflow.pub.domain.FlowDirect;
import com.gsralex.gflow.pub.enums.JobGroupStatusEnum;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/12/7
 */
public class FlowItemGuideTest {
    @Test
    public void listRoot() throws Exception {
        long groupId = 0;
        List<FlowItem> flowItemList = new ArrayList<>();
        FlowItem flowItem1 = new FlowItem();
        flowItem1.setIndex(1);
        flowItem1.setActionId(1);
        flowItemList.add(flowItem1);
        FlowItem flowItem2 = new FlowItem();
        flowItem2.setIndex(2);
        flowItem2.setActionId(2);
        flowItemList.add(flowItem2);

        List<FlowDirect> directList = new ArrayList<>();
        FlowDirect direct1 = new FlowDirect();
        direct1.setIndex(0);
        direct1.setNextIndex("1");
        directList.add(direct1);
        FlowDirect direct2 = new FlowDirect();
        direct2.setIndex(1);
        direct2.setNextIndex("2");
        directList.add(direct2);
        FlowGuide flowGuide = new FlowGuide(groupId, flowItemList, directList, JobGroupStatusEnum.EXECUTING);
        Assert.assertEquals(1, flowGuide.listRoot().size());
        Assert.assertEquals(flowItem1.getIndex(), flowGuide.listRoot().get(0).getIndex());
    }

    @Test
    public void listNeedAction() throws Exception {
        long groupId = 0;
        List<FlowItem> flowItemList = new ArrayList<>();
        FlowItem flowItem1 = new FlowItem();
        flowItem1.setIndex(1);
        flowItem1.setActionId(1);
        flowItemList.add(flowItem1);
        FlowItem flowItem2 = new FlowItem();
        flowItem2.setIndex(2);
        flowItem2.setActionId(2);
        flowItemList.add(flowItem2);
        FlowItem flowItem3 = new FlowItem();
        flowItem3.setIndex(3);
        flowItem3.setActionId(3);
        flowItemList.add(flowItem3);

        List<FlowDirect> directList = new ArrayList<>();
        FlowDirect direct1 = new FlowDirect();
        direct1.setIndex(0);
        direct1.setNextIndex("1");
        directList.add(direct1);
        FlowDirect direct2 = new FlowDirect();
        direct2.setIndex(1);
        direct2.setNextIndex("2");
        directList.add(direct2);
        FlowDirect direct3 = new FlowDirect();
        direct3.setIndex(1);
        direct3.setNextIndex("3");
        directList.add(direct3);

        FlowGuide flowGuide = new FlowGuide(groupId, flowItemList, directList, JobGroupStatusEnum.EXECUTING);
        flowGuide.updateNodeOk(1, true);
        Assert.assertEquals(2, flowGuide.listNeedAction(1).size());
        Assert.assertEquals(2, flowGuide.listNeedAction(1).get(0).getIndex());
        Assert.assertEquals(3, flowGuide.listNeedAction(1).get(1).getIndex());
    }

    @Test
    public void listContinueAction() throws Exception {
        long groupId = 0;
        List<FlowItem> flowItemList = new ArrayList<>();
        FlowItem flowItem1 = new FlowItem();
        flowItem1.setIndex(1);
        flowItem1.setActionId(1);
        flowItemList.add(flowItem1);
        FlowItem flowItem2 = new FlowItem();
        flowItem2.setIndex(2);
        flowItem2.setActionId(2);
        flowItemList.add(flowItem2);
        FlowItem flowItem3 = new FlowItem();
        flowItem3.setIndex(3);
        flowItem3.setActionId(3);
        flowItemList.add(flowItem3);

        List<FlowDirect> directList = new ArrayList<>();
        FlowDirect direct1 = new FlowDirect();
        direct1.setIndex(0);
        direct1.setNextIndex("1");
        directList.add(direct1);
        FlowDirect direct2 = new FlowDirect();
        direct2.setIndex(1);
        direct2.setNextIndex("2");
        directList.add(direct2);
        FlowDirect direct3 = new FlowDirect();
        direct3.setIndex(1);
        direct3.setNextIndex("3");
        directList.add(direct3);

        FlowGuide flowGuide = new FlowGuide(groupId, flowItemList, directList, JobGroupStatusEnum.EXECUTING);
        Assert.assertEquals(1, flowGuide.listContinueAction().size());
        flowGuide.updateNodeOk(1, true);
        Assert.assertEquals(2, flowGuide.listContinueAction().size());
        flowGuide.updateNodeOk(2, true);
        Assert.assertEquals(1, flowGuide.listContinueAction().size());
        flowGuide.updateNodeOk(3, true);
        Assert.assertEquals(0, flowGuide.listContinueAction().size());
    }
}