package com.gsralex.gflow.scheduler.sql;

import com.gsralex.gflow.core.domain.GFlowExecuteConfig;

import java.util.List;

public interface ExecuteDao {


    List<GFlowExecuteConfig> listExecuteConfig();



}