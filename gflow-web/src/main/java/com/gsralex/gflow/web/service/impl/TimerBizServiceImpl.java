package com.gsralex.gflow.web.service.impl;

import com.gsralex.gflow.core.rpc.client.RpcClientFactory;
import com.gsralex.gflow.scheduler.client.TimerService;
import com.gsralex.gflow.scheduler.client.domain.TimerReq;
import com.gsralex.gflow.web.WebContext;
import com.gsralex.gflow.web.dao.TimerDao;
import com.gsralex.gflow.web.model.TimerVo;
import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.resp.Resp;
import com.gsralex.gflow.web.resp.ResultResp;
import com.gsralex.gflow.web.service.TimerBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Service
public class TimerBizServiceImpl implements TimerBizService {

    @Autowired
    private TimerDao timerDao;

    private WebContext context = WebContext.getInstance();

    @Override
    public Resp saveTimer(TimerReq req) {
        TimerService timerService = RpcClientFactory.create(TimerService.class, WebContext.getInstance().getSchedulerIpManager());
        timerService.addTimer(req);
        return new Resp();
    }

    @Override
    public Resp updateTimer(TimerReq req) {
        TimerService timerService = RpcClientFactory.create(TimerService.class, WebContext.getInstance().getSchedulerIpManager());
        timerService.updateTimer(req);
        return new Resp();
    }

    @Override
    public Resp removeTimer(long id) {
        TimerService timerService = RpcClientFactory.create(TimerService.class, WebContext.getInstance().getSchedulerIpManager());
        timerService.removeTimer(id);
        return new Resp();
    }

    @Override
    public ResultResp getTimer(long id) {
        return ResultResp.wrapData(timerDao.getTimer(id));
    }

    @Override
    public PageResp listTimer(int pageSize, int pageIndex) {
        List<TimerVo> list = timerDao.listTimer(pageSize, pageIndex);
        int cnt = timerDao.countTimer();
        PageResp resp = new PageResp();
        resp.setData(list);
        resp.setCnt(cnt);
        resp.setPageIndex(pageIndex);
        resp.setPageSize(pageSize);
        return resp;
    }
}
