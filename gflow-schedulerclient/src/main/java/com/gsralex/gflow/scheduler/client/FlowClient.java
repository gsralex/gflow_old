package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.scheduler.client.action.flow.FlowGroup;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface FlowClient {
    Resp setFlowGroup(FlowGroup flowGroup);
}