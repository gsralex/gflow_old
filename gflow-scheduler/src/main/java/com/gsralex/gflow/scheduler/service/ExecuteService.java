package com.gsralex.gflow.scheduler.service;

import com.gsralex.gflow.scheduler.domain.flow.GFlowExecuteConfig;

import java.util.List;

public interface ExecuteService {

    List<GFlowExecuteConfig> getNeedExecuteConfig();
 }
