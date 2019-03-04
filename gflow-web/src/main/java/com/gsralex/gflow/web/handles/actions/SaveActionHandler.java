package com.gsralex.gflow.web.handles.actions;

import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.scheduler.client.action.action.ActionReq;
import com.gsralex.gflow.web.handles.ClientManager;
import com.gsralex.gflow.web.handles.HttpHandler;

/**
 * @author gsralex
 * @version 2019/3/4
 */
public class SaveActionHandler implements HttpHandler<ActionReq, Resp> {

    @Override
    public Resp doAction(ActionReq actionReq) {
        return ClientManager.getActionClient().saveAction(actionReq);
    }

    @Override
    public String getRequestPath() {
        return "/action/save";
    }
}
