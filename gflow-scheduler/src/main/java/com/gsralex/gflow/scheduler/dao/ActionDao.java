package com.gsralex.gflow.scheduler.dao;

import com.gsralex.gflow.scheduler.domain.*;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public interface ActionDao {


    Action getAction(long id);

    List<ActionTag> listActionTag();

}
