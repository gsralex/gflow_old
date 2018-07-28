package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.scheduler.domain.flow.GFlowExecuteConfig;
import com.gsralex.gflow.scheduler.domain.flow.GFlowTrigger;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface ConfigDao {

    List<GFlowExecuteConfig> getExecuteActiveList();

    List<GFlowTrigger> getTriggerList(long triggerGroupId);


}
