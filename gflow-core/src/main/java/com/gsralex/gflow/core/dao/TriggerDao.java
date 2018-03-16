package com.gsralex.gflow.core.dao;

import com.gsralex.gflow.core.domain.GFlowJobGroup;
import com.gsralex.gflow.core.domain.GFlowTriggerGroup;

import java.util.List;

/**
 * @author gsralex
 * @date 2018/2/17
 */
public interface TriggerDao {


    List<GFlowJobGroup> listJobGroup(List<Long> groupIdList, int ds);
}
