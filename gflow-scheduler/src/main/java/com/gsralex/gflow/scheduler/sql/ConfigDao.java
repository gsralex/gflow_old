package com.gsralex.gflow.scheduler.sql;

import com.gsralex.gflow.core.domain.Action;
import com.gsralex.gflow.core.domain.ActionTag;
import com.gsralex.gflow.core.domain.ExecuteConfig;
import com.gsralex.gflow.core.domain.Flow;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface ConfigDao {


    Action getAction(long id);

    List<ActionTag> listActionTag();

    List<ExecuteConfig> listExecuteConfig();

    List<Flow> getFlowList(long triggerGroupId);

}
