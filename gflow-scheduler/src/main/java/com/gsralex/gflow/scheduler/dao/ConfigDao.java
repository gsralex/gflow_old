package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.scheduler.domain.flow.GFlowAction;
import com.gsralex.gflow.scheduler.domain.flow.GFlowExecuteConfig;
import com.gsralex.gflow.scheduler.domain.flow.GFlowTrigger;
import com.gsralex.gflow.scheduler.domain.flow.GFlowTriggerGroup;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface ConfigDao {


    GFlowAction getAction(long id);

    List<GFlowExecuteConfig> getExecuteActiveList();

    List<GFlowTrigger> getTriggerList(long triggerGroupId);

    List<GFlowTrigger> getNeedActionList(long triggerGroupId, long triggerId);


}
