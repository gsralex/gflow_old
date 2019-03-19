package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.core.domain.ActionPo;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface ActionDao {

    ActionPo getAction(long id);
}
