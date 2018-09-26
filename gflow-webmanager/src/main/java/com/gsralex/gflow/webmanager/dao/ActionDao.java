package com.gsralex.gflow.webmanager.dao;

import com.gsralex.gflow.core.domain.GFlowAction;
import com.gsralex.gflow.webmanager.model.PageDto;


/**
 * @author gsralex
 * @version 2018/9/5
 */
public interface ActionDao {


    boolean saveAction(GFlowAction action);

    boolean existsActionClass(long id, String className);

    PageDto listAction(int pageSize, int pageIndex);
}
