package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.core.domain.GFlowExecuteConfig;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public interface TimeService {

    List<GFlowExecuteConfig> listNeedActionGroup();
}
