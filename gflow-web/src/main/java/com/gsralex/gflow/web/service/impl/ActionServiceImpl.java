package com.gsralex.gflow.web.service.impl;

import com.gsralex.gflow.pub.domain.Action;
import com.gsralex.gflow.web.dao.ActionDao;
import com.gsralex.gflow.web.resp.PageResp;
import com.gsralex.gflow.web.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    protected ActionDao actionDao;


    @Override
    public boolean saveAction(String name, String className, String tag) {
        Action action = new Action();
        action.setName(name);
        action.setClassName(className);
        action.setTag(tag);
        action.setDel(false);
        action.setCreateTime(System.currentTimeMillis());
        return actionDao.saveAction(action);
    }

    @Override
    public boolean updateAction(long id, String name, String className, String tag) {
        Action action = actionDao.getAction(id);
        if (action != null) {
            action.setName(name);
            action.setClassName(className);
            action.setTag(tag);
            return actionDao.updateAction(action);
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAction(long id) {
        return actionDao.removeAction(id);
    }

    @Override
    public Action getAction(long id) {
        return actionDao.getAction(id);
    }

    @Override
    public PageResp listAction(String tag, String name, String className, int pageSize, int pageIndex) {
        List<Action> list = actionDao.listAction(tag, name, className, pageSize, pageIndex);
        int cnt = actionDao.countAction(tag, name, className);
        PageResp dto = new PageResp();
        dto.setData(list);
        dto.setCnt(cnt);
        dto.setPageIndex(pageIndex);
        dto.setPageSize(pageSize);
        return dto;
    }
}
