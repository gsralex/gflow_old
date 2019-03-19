package com.gsralex.gflow.scheduler.flow;
import com.gsralex.gflow.core.domain.FlowDirectPo;
import com.gsralex.gflow.core.domain.FlowItemPo;
import com.gsralex.gflow.core.enums.JobGroupStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/12/7
 */
public class FlowItemPoGuideTest {
    @Test
    public void listRoot() throws Exception {
        long groupId = 0;
        List<FlowItemPo> flowItemPoList = new ArrayList<>();
        FlowItemPo flowItemPo1 = new FlowItemPo();
        flowItemPo1.setIndex(1);
        flowItemPo1.setActionId(1);
        flowItemPoList.add(flowItemPo1);
        FlowItemPo flowItemPo2 = new FlowItemPo();
        flowItemPo2.setIndex(2);
        flowItemPo2.setActionId(2);
        flowItemPoList.add(flowItemPo2);

        List<FlowDirectPo> directList = new ArrayList<>();
        FlowDirectPo direct1 = new FlowDirectPo();
        direct1.setIndex(0);
        direct1.setNextIndex("1");
        directList.add(direct1);
        FlowDirectPo direct2 = new FlowDirectPo();
        direct2.setIndex(1);
        direct2.setNextIndex("2");
        directList.add(direct2);
        FlowGuide flowGuide = new FlowGuide(groupId, flowItemPoList, directList, JobGroupStatus.EXECUTING);
        Assert.assertEquals(1, flowGuide.listRoot().size());
        Assert.assertEquals(flowItemPo1.getIndex(), flowGuide.listRoot().get(0).getIndex());
    }

    @Test
    public void listNeedAction() throws Exception {
        long groupId = 0;
        List<FlowItemPo> flowItemPoList = new ArrayList<>();
        FlowItemPo flowItemPo1 = new FlowItemPo();
        flowItemPo1.setIndex(1);
        flowItemPo1.setActionId(1);
        flowItemPoList.add(flowItemPo1);
        FlowItemPo flowItemPo2 = new FlowItemPo();
        flowItemPo2.setIndex(2);
        flowItemPo2.setActionId(2);
        flowItemPoList.add(flowItemPo2);
        FlowItemPo flowItemPo3 = new FlowItemPo();
        flowItemPo3.setIndex(3);
        flowItemPo3.setActionId(3);
        flowItemPoList.add(flowItemPo3);

        List<FlowDirectPo> directList = new ArrayList<>();
        FlowDirectPo direct1 = new FlowDirectPo();
        direct1.setIndex(0);
        direct1.setNextIndex("1");
        directList.add(direct1);
        FlowDirectPo direct2 = new FlowDirectPo();
        direct2.setIndex(1);
        direct2.setNextIndex("2");
        directList.add(direct2);
        FlowDirectPo direct3 = new FlowDirectPo();
        direct3.setIndex(1);
        direct3.setNextIndex("3");
        directList.add(direct3);

        FlowGuide flowGuide = new FlowGuide(groupId, flowItemPoList, directList, JobGroupStatus.EXECUTING);
        flowGuide.updateNodeOk(1, true);
        Assert.assertEquals(2, flowGuide.listNeedAction(1).size());
        Assert.assertEquals(2, flowGuide.listNeedAction(1).get(0).getIndex());
        Assert.assertEquals(3, flowGuide.listNeedAction(1).get(1).getIndex());
    }

    @Test
    public void listContinueAction() throws Exception {
        long groupId = 0;
        List<FlowItemPo> flowItemPoList = new ArrayList<>();
        FlowItemPo flowItemPo1 = new FlowItemPo();
        flowItemPo1.setIndex(1);
        flowItemPo1.setActionId(1);
        flowItemPoList.add(flowItemPo1);
        FlowItemPo flowItemPo2 = new FlowItemPo();
        flowItemPo2.setIndex(2);
        flowItemPo2.setActionId(2);
        flowItemPoList.add(flowItemPo2);
        FlowItemPo flowItemPo3 = new FlowItemPo();
        flowItemPo3.setIndex(3);
        flowItemPo3.setActionId(3);
        flowItemPoList.add(flowItemPo3);

        List<FlowDirectPo> directList = new ArrayList<>();
        FlowDirectPo direct1 = new FlowDirectPo();
        direct1.setIndex(0);
        direct1.setNextIndex("1");
        directList.add(direct1);
        FlowDirectPo direct2 = new FlowDirectPo();
        direct2.setIndex(1);
        direct2.setNextIndex("2");
        directList.add(direct2);
        FlowDirectPo direct3 = new FlowDirectPo();
        direct3.setIndex(1);
        direct3.setNextIndex("3");
        directList.add(direct3);

        FlowGuide flowGuide = new FlowGuide(groupId, flowItemPoList, directList, JobGroupStatus.EXECUTING);
        Assert.assertEquals(1, flowGuide.listContinueAction().size());
        flowGuide.updateNodeOk(1, true);
        Assert.assertEquals(2, flowGuide.listContinueAction().size());
        flowGuide.updateNodeOk(2, true);
        Assert.assertEquals(1, flowGuide.listContinueAction().size());
        flowGuide.updateNodeOk(3, true);
        Assert.assertEquals(0, flowGuide.listContinueAction().size());
    }
}