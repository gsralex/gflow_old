package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.core.domain.GFlowAction;
import com.gsralex.gflow.core.domain.GFlowExecuteConfig;
import com.gsralex.gflow.core.domain.GFlowTrigger;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface ConfigDao {


    GFlowAction getAction(long id);

    List<GFlowExecuteConfig> getExecuteActiveList();

    List<GFlowTrigger> getTriggerList(long triggerGroupId);

}
