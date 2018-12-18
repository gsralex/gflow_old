package com.gsralex.gflow.scheduler.sql;

import com.gsralex.gflow.scheduler.domain.TimerConfig;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/12/18
 */
public interface TimerDao {

    boolean saveTimer(TimerConfig config);

    boolean updateTimer(TimerConfig config);

    boolean deleteTimer(long id);

    TimerConfig getTimer(long id);

    List<TimerConfig> listTimer();
}
