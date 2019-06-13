package com.gsralex.gflow.scheduler.service;

import com.gsralex.gflow.core.domain.TimerConfigPo;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface BizTimerService {

    boolean saveTimer(long flowGroupId, boolean active, String time);

    boolean updateTimer(long id, long flowGroupId, boolean active, String time);

    boolean removeTimer(long id);

    List<TimerConfigPo> listTimer();
}
