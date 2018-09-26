package com.gsralex.gflow.webmanager.service.impl;

import com.gsralex.gflow.core.domain.GFlowAction;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.webmanager.model.PageDto;
import com.gsralex.gflow.webmanager.model.ResultDto;
import com.gsralex.gflow.webmanager.dao.ActionDao;
import com.gsralex.gflow.webmanager.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2018/9/5
 */
@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionDao actionDao;

    @Override
    public ResultDto saveAction(String className, String description) {
        ResultDto r = new ResultDto();
        if (!actionDao.existsActionClass(0, className)) {
            GFlowAction action = new GFlowAction();
            action.setClassName(className);
            action.setCreateTime(DtUtils.getUnixTime());
            action.setModTime(DtUtils.getUnixTime());
            action.setName(description);
            actionDao.saveAction(action);
        } else {
            r.setCode(-1);
            r.setMsg("重复的classname");
        }
        return r;
    }

    @Override
    public PageDto listAction(int pageSize, int pageIndex) {
        return actionDao.listAction(pageSize, pageIndex);
    }
}
