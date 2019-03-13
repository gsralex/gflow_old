package com.gsralex.gflow.scheduler.service;

import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.hb.ExecutorNode;
import com.gsralex.gflow.scheduler.hb.SchedulerNode;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/13
 */
public interface HbService {

    /**
     * executor-> scheduler
     *
     * @param ip  ip
     * @param tag 标签
     * @return
     */
    List<IpAddr> executorHb(IpAddr ip, String tag);

    /**
     * scheduler slave -> master
     *
     * @param ip
     * @return 返回从节点
     */
    List<ExecutorNode> schedulerHb(IpAddr ip);

    List<SchedulerNode> listSchedulerNode();

    List<ExecutorNode> listExecutorNode();
}
