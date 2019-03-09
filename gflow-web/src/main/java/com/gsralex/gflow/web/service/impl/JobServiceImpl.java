package com.gsralex.gflow.web.service.impl;

import com.gsralex.gflow.web.dao.JobDao;
import com.gsralex.gflow.web.model.JobGroupVo;
import com.gsralex.gflow.web.model.JobVo;
import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.resp.ResultResp;
import com.gsralex.gflow.web.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Repository
public class JobServiceImpl implements JobService {
    @Autowired
    private JobDao jobDao;

    @Override
    public ResultResp getJobGroup(long id) {
        JobGroupVo vo = jobDao.getJobGroup(id);
        ResultResp dto = new ResultResp();
        dto.setData(vo);
        return dto;
    }

    @Override
    public PageResp listJobGroup(long flowGroupId, String date, int pageSize, int pageIndex) {
        List<JobGroupVo> list = jobDao.listJobGroup(flowGroupId, date, pageSize, pageIndex);
        int cnt = jobDao.countJobGroup(flowGroupId, date);
        PageResp dto = new PageResp();
        dto.setData(list);
        dto.setCnt(cnt);
        dto.setPageSize(pageSize);
        dto.setPageIndex(pageIndex);
        return dto;
    }

    @Override
    public ResultResp listJob(long jobGroupId) {
        ResultResp dto = new ResultResp();
        List<JobVo> list = jobDao.listJob(jobGroupId);
        dto.setData(list);
        return dto;
    }

    @Override
    public PageResp listJob(long actionId, String date, int pageSize, int pageIndex) {
        List<JobVo> list = jobDao.listJob(actionId, date, pageSize, pageIndex);
        int cnt = jobDao.countJob(actionId, date);
        PageResp dto = new PageResp();
        dto.setData(list);
        dto.setCnt(cnt);
        dto.setPageSize(pageSize);
        dto.setPageIndex(pageIndex);
        return dto;
    }
}
