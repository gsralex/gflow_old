package com.gsralex.gflow.web.dao;

import com.gsralex.gflow.web.model.JobGroupVo;
import com.gsralex.gflow.web.model.JobVo;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface JobDao {

    JobGroupVo getJobGroup(long id);

    List<JobGroupVo> listJobGroup(long flowGroupId, String date, int pageSize, int pageIndex);

    int countJobGroup(long flowGroupId, String date);

    List<JobVo> listJob(long jobGroupId);

    List<JobVo> listJob(long actionId,String date,int pageSize,int pageIndex);

    int countJob(long actionId,String date);
}
