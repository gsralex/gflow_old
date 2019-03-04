package com.gsralex.gflow.scheduler.client.impl;

import com.gsralex.gflow.pub.action.IdReq;
import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.thriftgen.TIdReq;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.action.TAction;
import com.gsralex.gflow.pub.thriftgen.action.TActionListResp;
import com.gsralex.gflow.pub.thriftgen.action.TActionReq;
import com.gsralex.gflow.pub.thriftgen.action.TActionService;
import com.gsralex.gflow.scheduler.client.ActionClient;
import com.gsralex.gflow.scheduler.client.ClientTransportException;
import com.gsralex.gflow.scheduler.client.action.action.Action;
import com.gsralex.gflow.scheduler.client.action.action.ActionListReq;
import com.gsralex.gflow.scheduler.client.action.action.ActionListResp;
import com.gsralex.gflow.scheduler.client.action.action.ActionReq;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/3
 */
public class ActionClientImpl implements ActionClient {

    private static final Logger LOG = LoggerFactory.getLogger(ActionClientImpl.class);

    private IpAddr ip;
    private String accessToken;

    public ActionClientImpl(IpAddr ip, String accessToken) {
        this.ip = ip;
        this.accessToken = accessToken;
    }


    @Override
    public Resp saveAction(ActionReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TActionService.Client client) throws TException {
                TActionReq tReq = new TActionReq();
                tReq.setActionName(req.getName());
                tReq.setClassName(req.getClassName());
                tReq.setTag(req.getTag());
                TResp tResp = client.saveAction(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return execute(new Callback());
    }

    @Override
    public Resp updateAction(ActionReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TActionService.Client client) throws TException {
                TActionReq tReq = new TActionReq();
                tReq.setId(req.getId());
                tReq.setActionName(req.getName());
                tReq.setClassName(req.getClassName());
                tReq.setTag(req.getTag());
                TResp tResp = client.updateAction(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return execute(new Callback());
    }

    @Override
    public Resp removeAction(IdReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TActionService.Client client) throws TException {
                TIdReq tReq = new TIdReq();
                tReq.setId(req.getId());
                TResp tResp = client.removeAction(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return execute(new Callback());
    }

    @Override
    public ActionListResp listAction(ActionListReq req) {
        class Callback implements ClientCallback<ActionListResp> {

            @Override
            public ActionListResp doAction(TActionService.Client client) throws TException {
                TActionListResp tResp = client.listAction(req.getPageSize(), req.getPageIndex());
                ActionListResp resp = new ActionListResp();
                List<Action> actionList = new ArrayList<>();
                List<TAction> tActionList = tResp.getActionList();
                for (TAction tAction : tActionList) {
                    Action action = new Action();
                    action.setTag(tAction.getTag());
                    action.setClassName(tAction.getClassName());
                    action.setName(tAction.getName());
                    action.setCreateTime(tAction.getCreateTime());
                    actionList.add(action);
                }
                resp.setActionList(actionList);
                return resp;
            }
        }
        return execute(new Callback());
    }

    public interface ClientCallback<T> {
        T doAction(TActionService.Client client) throws TException;
    }

    public <T> T execute(ClientCallback<T> callback) {
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "action");
            TActionService.Client client = new TActionService.Client(multiProtocol);
            return callback.doAction(client);
        } catch (Exception e) {
            LOG.error("ActionClientImpl.execute", e);
            throw new ClientTransportException(e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}
