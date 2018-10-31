package com.gsralex.gflow.scheduler.sql;

import com.gsralex.gflow.core.domain.Action;
import com.gsralex.gflow.core.domain.ExecuteConfig;
import com.gsralex.gflow.core.domain.Flow;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface ConfigDao {


    Action getAction(long id);

    List<ExecuteConfig> listExecuteConfig();

    List<Flow> getTriggerList(long triggerGroupId);

}
