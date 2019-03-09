package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.pub.domain.Action;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface ActionDao {

    Action getAction(long id);
}
