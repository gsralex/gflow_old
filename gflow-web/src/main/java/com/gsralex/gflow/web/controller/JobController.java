package com.gsralex.gflow.web.controller;

import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.resp.ResultResp;
import com.gsralex.gflow.web.service.JobBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@RestController
@CrossOrigin
public class JobController {

    @Autowired
    private JobBizService jobBizService;

    @RequestMapping(value = "/jobgroup/get")
    public ResultResp getJobGroup(long id) {
        return jobBizService.getJobGroup(id);
    }


    @RequestMapping(value = "/jobgroup/querylist")
    public PageResp listJobGroupQuery(long flowGroupId, String date, int pageSize, int pageIndex) {
        return jobBizService.listJobGroup(flowGroupId, date, pageSize, pageIndex);
    }

    @RequestMapping(value = "/job/list")
    public ResultResp listJob(long jobGroupId) {
        return jobBizService.listJob(jobGroupId);
    }

    @RequestMapping(value = "/job/querylist")
    public PageResp listJobQuery(@RequestParam(defaultValue = "0") long actionId, String date, int pageSize, int pageIndex) {
        return jobBizService.listJob(actionId, date, pageSize, pageIndex);
    }
}
