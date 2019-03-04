package com.gsralex.gflow.scheduler.client;

import com.gsralex.gflow.pub.action.IdReq;
import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.scheduler.client.action.action.ActionListReq;
import com.gsralex.gflow.scheduler.client.action.action.ActionListResp;
import com.gsralex.gflow.scheduler.client.action.action.ActionReq;

/**
 * @author gsralex
 * @version 2019/3/3
 */
public interface ActionClient {


    Resp saveAction(ActionReq req);

    Resp updateAction(ActionReq req);

    Resp removeAction(IdReq req);

    ActionListResp listAction(ActionListReq req);

}
