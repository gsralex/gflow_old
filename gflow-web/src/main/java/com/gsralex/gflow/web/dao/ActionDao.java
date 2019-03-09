package com.gsralex.gflow.web.dao;

import com.gsralex.gflow.pub.domain.Action;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface ActionDao {

    boolean saveAction(Action action);

    boolean updateAction(Action action);

    boolean removeAction(long id);

    Action getAction(long id);

    List<Action> listAction(String tag, String name, String className, int pageSize, int pageIndex);

    int countAction(String tag, String name, String className);
}
