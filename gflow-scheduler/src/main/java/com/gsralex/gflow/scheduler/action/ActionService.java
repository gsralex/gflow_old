package com.gsralex.gflow.scheduler.action;

import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.dao.ActionDao;
import com.gsralex.gflow.scheduler.dao.impl.ActionDaoImpl;
import com.gsralex.gflow.scheduler.domain.Action;
import com.gsralex.gflow.scheduler.model.ResultDto;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/3
 */
public class ActionService {

    private ActionDao actionDao;

    public ActionService(SchedulerContext context) {
        actionDao = new ActionDaoImpl(context.getJdbcUtils());
    }

    public boolean saveAction(String name, String className, String tag) {
        Action action = new Action();
        action.setName(name);
        action.setClassName(className);
        action.setCreateTime(System.currentTimeMillis());
        action.setModTime(System.currentTimeMillis());
        action.setTag(tag);
        return actionDao.saveAction(action);
    }

    public ResultDto updateAction(long id, String name, String className, String tag) {
        ResultDto dto = new ResultDto();
        Action action = actionDao.getAction(id);
        if (action != null) {
            action.setName(name);
            action.setClassName(className);
            action.setTag(tag);
            actionDao.updateAction(action);
        } else {
            dto.setCode(-1);

        }
        return dto;
    }

    public boolean removeAction(long id) {
        return actionDao.removeAction(id);
    }


    public List<Action> listAction(int pageSize, int pageIndex) {
        return actionDao.listAction(pageSize, pageIndex);
    }
}
