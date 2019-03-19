package com.gsralex.gflow.web.dao;

import com.gsralex.gflow.core.domain.ActionPo;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface ActionDao {

    boolean saveAction(ActionPo actionPo);

    boolean updateAction(ActionPo actionPo);

    boolean removeAction(long id);

    ActionPo getAction(long id);

    List<ActionPo> listAction(String tag, String name, String className, int pageSize, int pageIndex);

    int countAction(String tag, String name, String className);
}
