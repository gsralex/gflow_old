package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.scheduler.domain.Action;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface ActionDao {


    boolean saveAction(Action action);

    boolean updateAction(Action action);

    boolean removeAction(long id);

    Action getAction(long id);

    List<Action> listAction(int pageSize, int pageIndex);

}
