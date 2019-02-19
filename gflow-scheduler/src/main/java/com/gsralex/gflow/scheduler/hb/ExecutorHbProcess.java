package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.pub.context.IpAddr;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/14
 */
public interface ExecutorHbProcess {

    List<IpAddr> listOnlineIp(String tag);

    IpAddr getOnlineIpSeq(String tag);
}
