package com.gsralex.gflow.scheduler.dao;


import com.gsralex.gflow.core.domain.TimerConfigPo;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/12/18
 */
public interface TimerDao {

    boolean saveTimer(TimerConfigPo config);

    boolean updateTimer(TimerConfigPo config);

    boolean deleteTimer(long id);

    TimerConfigPo getTimer(long id);

    List<TimerConfigPo> listTimer();
}
