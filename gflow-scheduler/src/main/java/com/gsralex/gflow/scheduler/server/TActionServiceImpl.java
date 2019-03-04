package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.pub.thriftgen.TIdReq;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.action.*;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.action.ActionService;
import com.gsralex.gflow.scheduler.domain.Action;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/4
 */
public class TActionServiceImpl implements TActionService.Iface {

    private SchedulerContext context;
    private ActionService actionService;

    public TActionServiceImpl(SchedulerContext context) {
        this.context = context;
        actionService = new ActionService(context);
    }

    @Override
    public TResp saveAction(TActionReq req) throws TException {
        return null;
    }

    @Override
    public TResp updateAction(TActionReq req) throws TException {
        return null;
    }

    @Override
    public TResp removeAction(TIdReq req) throws TException {
        return null;
    }

    @Override
    public TActionResp getAction(TIdReq req) throws TException {
        return null;
    }


    @Override
    public TActionListResp listAction(int pageSize, int pageIndex) throws TException {
        List<Action> actionList = actionService.listAction(pageSize, pageIndex);
        TActionListResp resp = new TActionListResp();

        List<TAction> tActionList = new ArrayList<>();
        for (Action action : actionList) {
            TAction tAction = new TAction();
            tAction.setId(action.getId());
            tAction.setName(action.getName());
            tAction.setClassName(action.getClassName());
            tAction.setCreateTime(action.getCreateTime());
            tActionList.add(tAction);
        }
        resp.setActionList(tActionList);
        return resp;
    }
}
