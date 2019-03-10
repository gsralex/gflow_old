package com.gsralex.gflow.web.service;

import com.gsralex.gflow.pub.domain.Action;
import com.gsralex.gflow.web.resp.PageResp;

/**
 * @author gsralex
 * @version 2019/3/8
 */
public interface ActionService {


    boolean saveAction(String name, String className, String tag);

    boolean updateAction(long id, String name, String className, String tag);

    boolean removeAction(long id);

    Action getAction(long id);

    PageResp listAction(String tag, String name, String className, int pageSize, int pageIndex);

}