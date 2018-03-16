package com.gsralex.gflow.core.dao;

import com.gsralex.gflow.core.domain.GFlowJobGroup;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public interface JobDao {

    boolean saveJobGroup(GFlowJobGroup jobGroup);
}
