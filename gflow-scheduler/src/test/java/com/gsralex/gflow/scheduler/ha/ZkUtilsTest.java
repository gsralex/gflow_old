package com.gsralex.gflow.scheduler.ha;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/18
 */
public class ZkUtilsTest {
    @Test
    public void listParentNode() throws Exception {
        List<ZkUtils.ZkNode> nodeList = ZkUtils.listParentNode("/gflow/scheduler/master");
        Assert.assertEquals(nodeList.size(), 3);
        Assert.assertEquals(nodeList.get(0).getPath(), "/gflow");
        Assert.assertEquals(nodeList.get(0).isParent(), true);
        Assert.assertEquals(nodeList.get(1).getPath(), "/gflow/scheduler");
        Assert.assertEquals(nodeList.get(1).isParent(), true);
        Assert.assertEquals(nodeList.get(2).getPath(), "/gflow/scheduler/master");
        Assert.assertEquals(nodeList.get(2).isParent(), false);

        List<ZkUtils.ZkNode> nodeList1 = ZkUtils.listParentNode("/gflow/scheduler/");
        Assert.assertEquals(nodeList1.size(), 2);

        List<ZkUtils.ZkNode> nodeList2 = ZkUtils.listParentNode("/gflow");
        Assert.assertEquals(nodeList2.size(), 1);
        Assert.assertEquals(nodeList2.get(0).getPath(), "/gflow");
        Assert.assertEquals(nodeList2.get(0).isParent(), false);


    }

}