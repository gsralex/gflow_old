package com.gsralex.gflow.web.controller;


import com.gsralex.gflow.web.req.TimerReq;
import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.resp.Resp;
import com.gsralex.gflow.web.resp.ResultResp;
import com.gsralex.gflow.web.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@RestController
@CrossOrigin
public class TimerController {


    @Autowired
    private TimerService service;

    @RequestMapping(value = "/timer/save", method = RequestMethod.POST)
    public Resp saveTimer(TimerReq req) {
        if (req.getId() != null && req.getId() > 0) {
            return service.updateTimer(req);
        } else {
            return service.saveTimer(req);
        }
    }

    @RequestMapping(value = "/timer/get", method = RequestMethod.GET)
    public ResultResp getTimer(long id) {
        return service.getTimer(id);
    }

    @RequestMapping(value = "/timer/remove", method = RequestMethod.POST)
    public Resp removeTimer(long id) {
        return service.removeTimer(id);
    }


    @RequestMapping(value = "/timer/list", method = RequestMethod.GET)
    public PageResp listTimer(int pageSize, int pageIndex) {
        return service.listTimer(pageSize, pageIndex);
    }

}
