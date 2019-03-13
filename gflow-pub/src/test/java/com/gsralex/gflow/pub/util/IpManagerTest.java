package com.gsralex.gflow.pub.util;

import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.context.IpManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/12
 */
public class IpManagerTest {
    @Test
    public void ipEquals() throws Exception {
        List<IpAddr> ipList1 = new ArrayList<>();
        ipList1.add(new IpAddr("192.168.1.1", 2001));
        ipList1.add(new IpAddr("192.168.1.2", 2001));
        ipList1.add(new IpAddr("192.168.1.3", 2001));


        List<IpAddr> ipList2 = new ArrayList<>();
        ipList2.add(new IpAddr("192.168.1.1", 2001));
        ipList2.add(new IpAddr("192.168.1.2", 2001));
        ipList2.add(new IpAddr("192.168.1.4", 2001));

        IpManager manager = new IpManager(ipList1);
        Assert.assertEquals(manager.ipEquals(ipList1, ipList2), false);


        List<IpAddr> ipList3 = new ArrayList<>();
        ipList3.add(new IpAddr("192.168.1.1", 2001));
        ipList3.add(new IpAddr("192.168.1.3", 2001));
        ipList3.add(new IpAddr("192.168.1.2", 2001));
        Assert.assertEquals(manager.ipEquals(ipList1, ipList3), true);

        List<IpAddr> ipList4 = new ArrayList<>();
        ipList4.add(new IpAddr("192.168.1.1", 2001));
        ipList4.add(new IpAddr("192.168.1.3", 2001));
        ipList4.add(new IpAddr("192.168.1.2", 2001));
        ipList4.add(new IpAddr("192.168.1.5", 2001));
        Assert.assertEquals(manager.ipEquals(ipList1, ipList4), false);

        List<IpAddr> ipList5 = new ArrayList<>();
        ipList5.add(new IpAddr("192.168.1.1", 2001));
        ipList5.add(new IpAddr("192.168.1.3", 2001));
        Assert.assertEquals(manager.ipEquals(ipList1, ipList5), false);
    }

}