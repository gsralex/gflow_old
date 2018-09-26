package com.gsralex.gflow.webmanager.web.controller;

import com.gsralex.gflow.webmanager.model.PageDto;
import com.gsralex.gflow.webmanager.model.ResultDto;
import com.gsralex.gflow.webmanager.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gsralex
 * @version 2018/9/5
 */
@RestController
@RequestMapping("/api/action")
public class ApiActionController {

    @Autowired
    private ActionService actionService;

    @RequestMapping("/save")
    public ResultDto saveAction(String className, String description) {
        return actionService.saveAction(className, description);
    }

    @RequestMapping("/list")
    public PageDto listAction(int pageSize, int pageIndex) {
        return actionService.listAction(pageSize, pageIndex);
    }


}
