package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.model.Result;

public interface ScheduleClient {

    Result scheduleGroup(long id, Parameter parameter);

    Result pauseGroup(long id);

    Result stopGroup(long id);

    Result scheduleAction(long id,Parameter parameter);

    Result setSettings(String key,String value);
}
