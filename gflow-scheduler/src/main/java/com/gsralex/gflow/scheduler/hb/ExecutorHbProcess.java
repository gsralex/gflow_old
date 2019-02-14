package com.gsralex.gflow.scheduler.hb;

import com.gsralex.gflow.core.context.IpAddr;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/14
 */
public interface ExecutorHbProcess {

    List<IpAddr> listOnlineIp(String tag);
}
