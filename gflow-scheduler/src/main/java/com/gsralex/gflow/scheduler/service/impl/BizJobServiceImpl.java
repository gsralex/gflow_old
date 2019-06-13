package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.scheduler.dao.JobDao;
import com.gsralex.gflow.scheduler.dao.TimerExecuteRecord;
import com.gsralex.gflow.scheduler.service.BizJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/27
 */
@Service
public class BizJobServiceImpl implements BizJobService {

    @Autowired
    private JobDao jobDao;

    @Override
    public List<TimerExecuteRecord> listJobGroupByTimer(List<Long> timerIds) {
        return jobDao.listJobGroupByTimer(timerIds);
    }
}
