package com.gsralex.gflow.scheduler.sql;

import com.gsralex.gflow.scheduler.domain.TimerConfig;

import java.util.List;

public interface ExecuteDao {


    List<TimerConfig> listExecuteConfig();



}
