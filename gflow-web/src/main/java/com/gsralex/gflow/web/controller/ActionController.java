package com.gsralex.gflow.web.controller;

import com.gsralex.gflow.pub.domain.Action;
import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.resp.ResultResp;
import com.gsralex.gflow.web.service.ActionService;
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
public class ActionController {

    @Autowired
    private ActionService service;

    @RequestMapping(value = "/action/save", method = RequestMethod.POST)
    public ResultResp saveAction(long id, String name, String className, String tag) {
        boolean ok;
        if (id > 0) {
            ok = service.updateAction(id, name, className, tag);
        } else {
            ok = service.saveAction(name, className, tag);
        }
        return ResultResp.wrapOk(ok);
    }

    @RequestMapping(value = "/action/get", method = RequestMethod.GET)
    public ResultResp getAction(long id) {
        Action action = service.getAction(id);
        return ResultResp.wrapData(action);
    }

    @RequestMapping(value = "/action/remove", method = RequestMethod.POST)
    public ResultResp removeAction(long id) {
        boolean ok = service.removeAction(id);
        return ResultResp.wrapOk(ok);
    }

    @RequestMapping(value = "/action/list", method = RequestMethod.GET)
    public PageResp listAction(String tag, String name, String className, int pageSize, int pageIndex) {
        return service.listAction(tag, name, className, pageSize, pageIndex);
    }
}
