package com.gsralex.gflow.web.service;

import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.resp.ResultResp;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface JobBizService {

    ResultResp getJobGroup(long id);

    PageResp listJobGroup(long flowGroupId, String date, int pageSize, int pageIndex);

    ResultResp listJob(long jobGroupId);

    PageResp listJob(long actionId, String date, int pageSize, int pageIndex);
}
