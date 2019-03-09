package com.gsralex.gflow.web.controller;

import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.resp.ResultResp;
import com.gsralex.gflow.web.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/jobgroup/get")
    public ResultResp getJobGroup(long id) {
        return jobService.getJobGroup(id);
    }


    @RequestMapping(value = "/jobgroup/querylist")
    public PageResp listJobGroupQuery(long flowGroupId, String date, int pageSize, int pageIndex) {
        return jobService.listJobGroup(flowGroupId, date, pageSize, pageIndex);
    }

    @RequestMapping(value = "/job/list")
    public ResultResp listJob(long jobGroupId) {
        return jobService.listJob(jobGroupId);
    }

    @RequestMapping(value = "/job/querylist")
    public PageResp listJobQuery(@RequestParam(defaultValue = "0") long actionId, String date, int pageSize, int pageIndex) {
        return jobService.listJob(actionId, date, pageSize, pageIndex);
    }
}
