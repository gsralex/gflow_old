package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.scheduler.domain.Flow;
import com.gsralex.gflow.scheduler.domain.FlowDirect;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/12/18
 */
public interface FlowDao {

    List<Flow> listFlow(long groupId);

    List<FlowDirect> listFlowDirect(long groupId);
}
