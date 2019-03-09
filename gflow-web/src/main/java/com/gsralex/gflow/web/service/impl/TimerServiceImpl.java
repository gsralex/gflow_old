package com.gsralex.gflow.web.service.impl;

import com.gsralex.gflow.pub.action.IdReq;
import com.gsralex.gflow.pub.action.Resp;
import com.gsralex.gflow.pub.context.IpSeqSelector;
import com.gsralex.gflow.scheduler.client.SchedulerClientFactory;
import com.gsralex.gflow.scheduler.client.TimerClient;
import com.gsralex.gflow.scheduler.client.action.timer.TimerReq;
import com.gsralex.gflow.web.WebContext;
import com.gsralex.gflow.web.dao.TimerDao;
import com.gsralex.gflow.web.model.TimerVo;
import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.resp.ResultResp;
import com.gsralex.gflow.web.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Service
public class TimerServiceImpl implements TimerService {

    @Autowired
    private TimerDao timerDao;

    private IpSeqSelector ipSelector;

    public TimerServiceImpl() {
        ipSelector = new IpSeqSelector(WebContext.getInstance().getSchedulerIps());
    }

    @Override
    public Resp saveTimer(TimerReq req) {
        TimerClient client = SchedulerClientFactory.createTimer(ipSelector.getIp(), "");
        return client.saveTimer(req);

    }

    @Override
    public Resp updateTimer(TimerReq req) {
//        SchedulerClient schedulerClient= SchedulerClientFactory.createScheduler(ipSelector.getIp(), "");
//        schedulerClient.scheduleAction(new JobReq());
        TimerClient client = SchedulerClientFactory.createTimer(ipSelector.getIp(), "");
        return client.updateTimer(req);
    }

    @Override
    public Resp removeTimer(long id) {
        TimerClient client = SchedulerClientFactory.createTimer(ipSelector.getIp(), "");
        IdReq idReq = new IdReq();
        idReq.setId(id);
        return client.removeTimer(idReq);
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
