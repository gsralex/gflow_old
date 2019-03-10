package com.gsralex.gflow.web.controller;

import com.gsralex.gflow.web.resp.ResultResp;
import com.gsralex.gflow.web.service.FlowService;
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
public class FlowController {

    @Autowired
    private FlowService flowService;

    @RequestMapping(value = "/flowgroup/list", method = RequestMethod.GET)
    public ResultResp listFlowGroup() {
        return flowService.listFlowGroup();
    }
}
