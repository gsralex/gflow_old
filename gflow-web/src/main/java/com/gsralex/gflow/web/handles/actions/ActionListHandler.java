package com.gsralex.gflow.web.handles.actions;

import com.gsralex.gflow.scheduler.client.action.action.ActionListReq;
import com.gsralex.gflow.scheduler.client.action.action.ActionListResp;
import com.gsralex.gflow.web.handles.ClientManager;
import com.gsralex.gflow.web.handles.HttpHandler;

/**
 * @author gsralex
 * @version 2019/3/4
 */
public class ActionListHandler implements HttpHandler<ActionListReq, ActionListResp> {

    @Override
    public ActionListResp doAction(ActionListReq actionListReq) {
        return ClientManager.getActionClient().listAction(actionListReq);
    }

    @Override
    public String getRequestPath() {
        return "/action/list";
    }
}
