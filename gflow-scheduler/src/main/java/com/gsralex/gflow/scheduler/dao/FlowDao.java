package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.pub.domain.Flow;
import com.gsralex.gflow.pub.domain.FlowDirect;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface FlowDao {

    List<Flow> listFlow(long groupId);

    List<FlowDirect> listFlowDirect(long groupId);
}
