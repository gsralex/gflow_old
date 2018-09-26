package com.gsralex.gflow.webmanager.service;

import com.gsralex.gflow.webmanager.model.PageDto;
import com.gsralex.gflow.webmanager.model.ResultDto;

/**
 * @author gsralex
 * @version 2018/9/5
 */
public interface ActionService {

    ResultDto saveAction(String className, String description);

    PageDto listAction(int pageSize, int pageIndex);
}
