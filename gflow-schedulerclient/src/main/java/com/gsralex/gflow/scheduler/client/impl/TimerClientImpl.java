package com.gsralex.gflow.scheduler.client.impl;

import com.gsralex.gflow.pub.action.IdReq;
import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.thriftgen.TIdReq;
import com.gsralex.gflow.pub.thriftgen.TResp;
import com.gsralex.gflow.pub.thriftgen.timer.TTimeReq;
import com.gsralex.gflow.pub.thriftgen.timer.TTimerService;
import com.gsralex.gflow.scheduler.client.ClientTransportException;
import com.gsralex.gflow.scheduler.client.TimerClient;
import com.gsralex.gflow.scheduler.client.action.timer.TimerReq;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2019/3/8
 */
public class TimerClientImpl implements TimerClient {

    private static final Logger LOG = LoggerFactory.getLogger(TimerClientImpl.class);

    private IpAddr ip;

    public TimerClientImpl(IpAddr ip) {
        this.ip = ip;
    }

    @Override
    public Resp saveTimer(TimerReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TTimerService.Client client) throws TException {
                TTimeReq tReq = new TTimeReq();
                tReq.setId(req.getId());
                tReq.setTime(req.getTime());
                tReq.setFlowGroupId(req.getFlowGroupId());
                tReq.setActive(req.getActive());
                TResp tResp = client.saveTimer(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return execute(new Callback(), ip);
    }

    @Override
    public Resp updateTimer(TimerReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TTimerService.Client client) throws TException {
                TTimeReq tReq = new TTimeReq();
                tReq.setId(req.getId());
                tReq.setTime(req.getTime());
                tReq.setFlowGroupId(req.getFlowGroupId());
                tReq.setActive(req.getActive());
                tReq.setAccessToken("");
                TResp tResp = client.updateTimer(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return execute(new Callback(), ip);
    }

    @Override
    public Resp removeTimer(IdReq req) {
        class Callback implements ClientCallback<Resp> {
            @Override
            public Resp doAction(TTimerService.Client client) throws TException {
                TIdReq tReq = new TIdReq();
                tReq.setId(req.getId());
                TResp tResp = client.removeTimer(tReq);
                Resp resp = new Resp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                return resp;
            }
        }
        return execute(new Callback(), ip);
    }

    public interface ClientCallback<T> {
        T doAction(TTimerService.Client client) throws TException;
    }

    public static <T> T execute(TimerClientImpl.ClientCallback<T> callback, IpAddr ip) {
        TTransport transport = new TSocket(ip.getIp(), ip.getPort());
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "timer");
            TTimerService.Client client = new TTimerService.Client(multiProtocol);
            return callback.doAction(client);
        } catch (Exception e) {
            LOG.error("SchedulerClient.execute", e);
            throw new ClientTransportException(e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}
