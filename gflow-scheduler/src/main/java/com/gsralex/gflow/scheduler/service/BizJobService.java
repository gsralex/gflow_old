package com.gsralex.gflow.scheduler.service;

import com.gsralex.gflow.scheduler.dao.TimerExecuteRecord;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/27
 */
public interface BizJobService {

    List<TimerExecuteRecord> listJobGroupByTimer(List<Long> timerIds);
}
