package com.gsralex.gflow.scheduler.sql;

import com.gsralex.gflow.scheduler.domain.*;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface ConfigDao {


    Action getAction(long id);

    List<ActionTag> listActionTag();

    List<TimerConfig> listExecuteConfig();

    List<Flow> listFlow(long groupId);

    List<FlowDirect> listFlowDirect(long groupId);
}
