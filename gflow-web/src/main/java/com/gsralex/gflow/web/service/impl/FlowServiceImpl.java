package com.gsralex.gflow.web.service.impl;

import com.gsralex.gflow.web.dao.FlowDao;
import com.gsralex.gflow.web.resp.ResultResp;
import com.gsralex.gflow.web.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2019/3/9
 */
@Service
public class FlowServiceImpl implements FlowService {

    @Autowired
    private FlowDao flowDao;

    @Override
    public ResultResp listFlowGroup() {
        return ResultResp.wrapData(flowDao.listFlowGroup());
    }
}
